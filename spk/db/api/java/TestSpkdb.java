import rfpk.spk.spkdb.*;
import java.sql.*;

public class TestSpkdb {
    public static void main(String args[]) {
	String username = "air";
	String password = "codered";
	String firstName = "Mike";
	String surname = "Jordan";
	final int maxTests = 33;
	String xmlSource = "<spksource></spksource>";

	boolean b = true;
	boolean target = true;
	String s = "connection";
	int i = 1;
 	long datasetId = 0;
	long newDatasetId = 0;
	long newerDatasetId = 0;
	long newestDatasetId = 0;
 	long modelId = 0;
	long newModelId = 0;
	long newerModelId = 0;
	long newestModelId = 0;
	long userId = 0;
	long jobId = 0;

	long newerJobId = 0;
	long newestJobId = 0;
	
	Connection conn;

	try {
	    conn = Spkdb.connect("spktest", "localhost.localdomain", "tester", "tester");
	} catch (Exception e) {
	    s += " >> " + e;
	    b = false;
	    ok(b, i, s);
	    return;
	}
	ok(b, i, s);

	for (i = 2; i <= maxTests; i++) {
	    try {
		switch (i) {
		case 2:
		    target = false;
		    s = "newUser";
		    {
			String n[] = {"username", "first_name", "surname"};
			String v[] = { username,  firstName, surname };
			long r = Spkdb.newUser(conn, n, v);		    
			b = r == 1;
		    }
		    break;
		case 3:
		    target = false;
		    s = "newUser";
		    {
			String n[] = {"password", "first_name", "surname"};
			String v[] = { password,  firstName, surname };
			long r = Spkdb.newUser(conn, n, v);		    
			b = r == 1;
		    }
		    break;
		case 4:
		    target = true;
		    {
			String n[] = {"username", "password", "first_name", "surname"};
			String v[] = { username,  password, firstName, surname };
			s = "newUser";
			userId = Spkdb.newUser(conn, n, v);		    
			s += " is user number " + userId;
			b = userId == 1;
		    }
		    break;
		case 5:
		    target = false;
		    s = "newUser";
		    {
			String n[] = {"username", "password", "first_name", "surname"};
			String v[] = { username,  password, firstName, surname };
			long r = Spkdb.newUser(conn, n, v);		    
			b = r == 1;
		    }
		    break;
		case 6:
		    target = true;
		    s = "updateUser";
		    {
			String n[] = {"first_name", "surname"};
			String v[] = {"Gerry",       "Peyton" };
			b = Spkdb.updateUser(conn, userId, n, v);
		    }
		    break;
		case 7:
		    target = false;
		    s = "updateUser";
		    {
			String n[] = {"username", "first_name"};
			String v[] = {"glove",    "Gary"      };
			b = Spkdb.updateUser(conn, userId, n, v);
		    }
		    break;
		case 8:
		    target = false;
		    s = "updateUser";
		    {
			String n[] = {"first_name", "username"};
			String v[] = {"Gary",       "glove"   };
			b = Spkdb.updateUser(conn, userId, n, v);
		    }
		    break;
		case 9:
		    target = true;
		    s = "getUser";
		    ResultSet rs = Spkdb.getUser(conn, userId);
		    if (rs.next()) {
			String name = rs.getString("first_name");
			b = name.compareTo("Gerry") == 0;
		    } 
		    else {
			s += ": no record for userId=" + userId;
			b = false;
		    } 
		    break;
		case 10:
		    target = true;
		    s = "newJob";
		    jobId = Spkdb.newJob(conn, 
					      userId,
					      "Abstract: Job 1",
					      33,
					      "1.01",
					      44,
					      "1.4.3",
					      xmlSource);
		    b = jobId != 0;
		    s += ": job number " + jobId;
		    break;
		case 11:
		    target = true;
		    s = "jobStatus";
		    rs = Spkdb.jobStatus(conn, jobId);
		    if (rs.next()) {
			String name = rs.getString("state_code");
			b = name.compareTo("q2c") == 0;
		    } 
		    else {
			s += ": no record for jobId=" + jobId;
			b = false;
		    } 
		    break;
		case 12:
		    Thread.sleep(1000);
		    target = true;
		    s = "newJob";

		    newerJobId = Spkdb.newJob(conn, 
					      userId,
					      "Abstract: job 2",
					      33,
					      "1.01",
					      44,
					      "1.4.3",
					      xmlSource);
		    b = newerJobId != 0;
		    s += ": job number " + newerJobId;

		    break;
		case 13:
		    Thread.sleep(1000);
		    target = true;
		    s = "newJob";
		    newestJobId = Spkdb.newJob(conn, 
					      userId,
					       "Abstract: job 3",
					      33,
					      "1.01",
					      44,
					      "1.4.3",
					      xmlSource);
		    b = newestJobId != 0;
		    s += ": job number " + newestJobId;
		    break;
		case 14:
		    target = true;
		    s = "userJobs, maxNum = 1";
		    rs = Spkdb.userJobs(conn, userId, 1);
		    if (rs.next()) {
			b = rs.getLong("job_id") == newestJobId;
		    } 
		    else {
			s += ": no record for userId = " + userId;
			b = false;
		    } 

		    break;
		case 15:
		    b = target = true;
		    s = "userJobs, maxNum = 3";
 		    rs = Spkdb.userJobs(conn, userId, 3);
		    long jj = newestJobId;

		    while (rs.next()) {
			long j;
			if ((j = rs.getLong("job_id")) != jj--) {
			    s += "; jobId" + j + " is out of order";
                            b = false;
			    break;
			}
		    }
		    s += "; " + (newestJobId - jj) + " were returned";
		    break;
		case 16:
		    b = target = true;
		    s = "endJob";
		    b = Spkdb.endJob(conn, newerJobId, "srun", "job report");
		    break;
		case 17:
		    b = target = false;
		    s = "endJob";
		    b = Spkdb.endJob(conn, newerJobId, "xxxx", "job report");
		    break;
		case 18:
		    target = true;
		    s = "jobReport";
		    String report = Spkdb.jobReport(conn, newerJobId);
		    b = report.compareTo("job report") == 0;
		    break;
		case 19:
		    target = false;
		    s = "jobReport";
		    report = Spkdb.jobReport(conn, newestJobId);
		    b = report.compareTo("job report") == 0;
		    break;
		case 20:
		    target = true;
		    s = "newDataset";
		    datasetId 
			= Spkdb.newDataset(conn, userId, "T1", "Dataset T1", "1 2 4 3");
		    s += ": datasetId = " + datasetId;
		    b = datasetId > 0;
		    break;
		case 21:
		    target = false;
		    s = "newDataset";
		    datasetId 
			= Spkdb.newDataset(conn, userId, "T1", "Dataset: X", "1 5 4 3");
		    s += ": datasetId = " + datasetId;
		    b = datasetId > 0;
		    break;
		case 22:
		    target = false;
		    s = "getDataset";
		    String dataset = Spkdb.getDataset(conn, datasetId);
		    b = dataset.compareTo("1 2 4 3") == 0;
		    break;
		case 23:
		    target = true;
		    s = "updateDataset";
		    {
			String n[] = {"abstract"};
			String v[] = {"dataset T1"   };
			b = Spkdb.updateDataset(conn, datasetId, n, v);
		    }
		    break;
		case 24:
		    target = false;
		    s = "updateDataset";
		    {
			String n[] = {"dataset_id", "abstract"};
			String v[] = {"55", "dataset T2"   };
			b = Spkdb.updateDataset(conn, datasetId, n, v);
		    }
		    break;
		case 25:
		    target = false;
		    s = "updateDataset";
		    {
			String n[] = {"abstract", "dataset_id"};
			String v[] = {"dataset T2", "66"   };
			b = Spkdb.updateDataset(conn, datasetId, n, v);
		    }
		    break;
		case 26:
		    b = target = true;
		    s = "userDatasets, maxNum = 3";
		    newDatasetId
			= Spkdb.newDataset(conn, userId, "T2", "Dataset T2", "1 4 4 3");
		    newerDatasetId 
			= Spkdb.newDataset(conn, userId, "T3", "Dataset T3", "6 2 4 3");
		    newestDatasetId
			= Spkdb.newDataset(conn, userId, "T4", "Dataset T4", "1 2 4 8");
		    		    
 		    rs = Spkdb.userDatasets(conn, userId, 3);
		    jj = newestDatasetId;

		    while (rs.next()) {
			long j;
			if ((j = rs.getLong("dataset_id")) != jj--) {
			    s += "; datasetId" + j + " is out of order";
                            b = false;
			    break;
			}
		    }
		    s += "; " + (newestDatasetId - jj) + " were returned";
		    break;
		case 27:
		    target = true;
		    s = "newModel";
		    modelId 
			= Spkdb.newModel(conn, userId, "M1", "Model M1", "1 2 4 3");
		    s += ": modelId = " + modelId;
		    b = modelId > 0;
		    break;
		case 28:
		    target = false;
		    s = "newModel";
		    modelId 
			= Spkdb.newModel(conn, userId, "M1", "Model: X", "1 5 4 3");
		    s += ": modelId = " + modelId;
		    b = modelId > 0;
		    break;
		case 29:
		    target = false;
		    s = "getModel";
		    String model = Spkdb.getModel(conn, modelId);
		    b = model.compareTo("1 2 4 3") == 0;
		    break;
		case 30:
		    target = true;
		    s = "updateModel";
		    {
			String n[] = {"abstract"};
			String v[] = {"model M1"   };
			b = Spkdb.updateModel(conn, modelId, n, v);
		    }
		    break;
		case 31:
		    target = false;
		    s = "updateModel";
		    {
			String n[] = {"model_id", "abstract"};
			String v[] = {"55", "model M2"   };
			b = Spkdb.updateModel(conn, modelId, n, v);
		    }
		    break;
		case 32:
		    target = false;
		    s = "updateModel";
		    {
			String n[] = {"abstract", "model_id"};
			String v[] = {"model M2", "66"   };
			b = Spkdb.updateModel(conn, modelId, n, v);
		    }
		    break;
		case 33:
		    b = target = true;
		    s = "userModels, maxNum = 3";
		    newModelId
			= Spkdb.newModel(conn, userId, "M2", "Model M2", "1 4 4 3");
		    newerModelId 
			= Spkdb.newModel(conn, userId, "M3", "Model M3", "6 2 4 3");
		    newestModelId
			= Spkdb.newModel(conn, userId, "M4", "Model M4", "1 2 4 8");
		    		    
 		    rs = Spkdb.userModels(conn, userId, 3);
		    jj = newestModelId;

		    while (rs.next()) {
			long j;
			if ((j = rs.getLong("model_id")) != jj--) {
			    s += "; modelId" + j + " is out of order";
                            b = false;
			    break;
			}
		    }
		    s += "; " + (newestModelId - jj) + " were returned";
		    break;

		default:
		    break;
		}
	    } catch (Exception e) {
		s += " >> " + e;
		b = false;
	    }
	    ok(b == target, i, s);
	}
	try {
	    b = Spkdb.disconnect(conn);
	} catch (Exception e) {
	    s += " >> " + e;
	    b = false;
	}
	ok(b, i, "disconnect");
    }
    private static void ok(boolean b, int i, String m) {
	String s = b ? "ok:\t" : "not ok:\t";
	s += i + " - " + m;
	System.out.println(s);
    }
}
