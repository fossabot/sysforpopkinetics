<?

require_once('DB.php');
//PEAR::setErrorHandling(PEAR_ERROR_TRIGGER);
PEAR::setErrorHandling (PEAR_ERROR_DIE);
//PEAR::setErrorHandling(PEAR_ERROR_PRINT);
// Database connection definition.
$dsn = array(
 'phptype'  => 'mysql',
 'hostspec' => 'whitechuck.rfpk.washington.edu',
 'database' => 'spkdb',
 'username' => 'daemon',
 'password' => 'daemon'
);

// this is for administration access, and is saved as a session var 
// with md5()
$special_key = "012af024aa-04";

$db = DB::connect($dsn);
//$db->setFetchMode(DB_FETCHMODE_ASSOC);
$db->setFetchMode(DB_FETCHMODE_OBJECT);

$debug = 1;

$minlengths = array ( 'username' => 5,
		      'password' => 4,
		    );
		   
		   

// define the steps through which a user will progress.
$steps = array ("Event Registration", "Your Information", "Set a Password", "Thank you");
$max_steps = sizeof($steps);

$admin_email = "vicini@u.washington.edu,ernst@u.washington.edu";

/****************************************************************************
 * show_errors()
 * 
 * called on any page that needs to show an error message.  Using styles
 * the user will see red bolded text using the "errortext" style definition
 *
 ****************************************************************************/

function show_errors( ) {
$tmp_var = "";
  if ( isset($_SESSION['errors']) && is_array($_SESSION['errors']) ) {
     if ( sizeof($_SESSION['errors']) > 0 ) {
  	$tmp_var = '<span class="errortext">';
  	foreach ( $_SESSION['errors'] as $index => $value ) {
    		$tmp_var = $tmp_var . $value . "<br /><br />";
  	}
  	$tmp_var = $tmp_var . '</span>';

	return $tmp_var;
     }
  }
return $tmp_var;
}


/****************************************************************************
 * show_all()
 * 
 * used to show all the variables in session and request scope on page
 *
 ****************************************************************************/
function show_all ( ) {
  $retval = "<strong>Session ID</strong>: " . session_id() . "<br><br>\n"
  		. "<strong>Session Variables</strong>:" .var_dump($_SESSION) . "<br><br>"  
  		. "<strong>Request Variables</strong>:  " . var_dump($_REQUEST);

return ($retval);
}

/****************************************************************************
 * show_all()
 * 
 * used to show all the variables in session and request scope on page
 *
 ****************************************************************************/
function valid_username ( $username ) {
  // connect to both spkutil and spkdb and check whether the accounts already
  // exist.
  global $db;

  $result = $db->query("select (select count(user_id) from spkdb.user where lower(username)=lower('" . $username . "')) as user_count, (select count(id) from spkutil.user_request where lower(username)=lower('" . $username . "')) as pending_count");

  if ( $result->fetchInto($row) ) {
     if ( $row->user_count > 0 || $row->pending_count > 0 ) return false;
  }
  else {
	throw("error with database");
	return false;
  }

  return true;
}


function size_check ( $field ) {
  global $minlengths;
  if ( strlen($_SESSION[$field]) < $minlengths[$field] ) return false;
  return true;
}

function add_error ($errorarray, $message) {
  $errorarray[sizeof($errorarray)] = $message;
  return $errorarray;
}

function not_null ( $string ) {
  if ( is_string($string) && strlen($string) > 0 ) { 
    return true;
  }

return false;
}

function check_required ( $fieldlist, $array, $errors ) {
   $required = array ();
   foreach ( $fieldlist as $key => $value ) {
   
   if ( ! not_null($array[$key]) ) {	
	$required[sizeof($required)] = $value;
   }	
  }

if ( ! is_array($errors) ) {
  $errors = array ();
}

// check to see if $required has size greater than 0.
if ( sizeof($required) > 0 ) {
	$tmp_string = "";
	foreach ( $required as $value ) {
	  $tmp_string = $tmp_string . "<em>" . $value . "</em>, ";
	}
	// remove the trailing comma.
	$tmp_string = substr($tmp_string,0,strlen($tmp_string)-2);
	if ( sizeof($required) > 1 ) {
	  $errors = add_error ($errors, $tmp_string . " are required fields.");
	}
	else {
	  $errors = add_error ($errors, $tmp_string . " is a required field.");
	}
}
return $errors;
}

function country_name ($id) {
  global $db;

  $result = $db->query("select name from spkutil.countries where id=" . $id);
  $result->fetchInto($row);

  if ( sizeof($row->name) > 0 ) {
    return $row->name;
  }
  else {
    return "Country ID: " . $id;
  }

}
function country_drop ($selected) {
global $db;
$db->setFetchMode(DB_FETCHMODE_OBJECT);

$retval = "";

$result = $db->query("select id, code, name from spkutil.countries");
 while ( $result->fetchInto($row) ) {
   if ( $row->id == $selected || ( sizeof($selected) <= 0 && $row->id == 184 )) {
    $retval = $retval . '<option selected value="' . $row->id . '">' . $row->name . '</option>' . "\n";
  } else {
    $retval = $retval . '<option value="' . $row->id . '">' . $row->name . '</option>' . "\n";
  }
}

return $retval;
}

function notify_admin ( )  {
  global $db, $admin_email;

// get number of pending requests

require_once ("Mail.php");
 
 $headers['From'] = "rfpk@u.washington.edu";
 $headers['To'] = $admin_email;
 $headers['Subject'] = "New RFPK Workshop Registration";


$message = '
-------- THIS IS AN AUTOMATED MESSAGE ---------

A new workshop registration request has been made.  Please point your
browser to the registrations management system at 

http://workshops.rfpk.washington.edu/admin.php

You can log in with your SPK user account.';

$mail_object =& Mail::factory('sendmail');
$mail_object->send($admin_email, $headers, $message);

 $mail_paolo =& Mail::factory('sendmail');
 $mail_paolo->send($headers['From'],$headers, $message);

return true;

}

function notify_customer ( $customer_email, $customer_firstname ) {

  require_once ("Mail.php");

  $headers['From'] = "Paolo Vicini <vicini@u.washington.edu>";
  $headers['To'] = $customer_email;
  $headers['Subject'] = "RFPK Workshop Registration";
  $headers['Bcc'] = $headers['From'];

$message = 'Dear ' . $customer_firstname . ',

Thank you for registering for an RFPK workshop. The information you provided
during registration will be used to enroll you in the ' . $_SESSION['event_name'] . '.

Please note that the workshop is currently scheduled to last for two full days. 
Also, please note that the workshop schedule is subject to change, and we reserve the 
right to cancel the workshop in case of low enrollment.

You will receive more information once your registration has been reviewed
by our staff. Once space has been allocated in the workshop, we will contact
you via e-mail at the address you gave to provide further instructions on
where to send your payment and more information about the workshop venue.

Thank you for your interest in our RFPK workshops. Questions about the
workshops should be directed to David Salinger at salinger@u.washington.edu.

Best wishes,

Paolo Vicini, Ph.D.
Associate Professor of Bioengineering
 
Mailing Address:
Resource Facility for Population Kinetics Department of Bioengineering, Box
355061 University of Washington Seattle, WA 98195-5061
 
Courier Address:
William H. Foege Building
1705 NE Pacific Street, Room N410G
University of Washington
Seattle, WA 98195-5061
 
Phone (206)616-1133
 
Fax (206)685-3300 (shared) 
 
http://depts.washington.edu/bioe/people/core/vicini/vicini.html';

$mail_object =& Mail::factory('sendmail');
$mail_object->send($customer_email, $headers, $message);

 $mail_paolo =& Mail::factory('sendmail');
 $mail_paolo->send($headers['From'], $headers, $message);

return true;
}

function validate_email ( $field ) {
  // check to see if it is in the format xxx@xxxx.xx
  if(!eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $field) ) {
    return $false;
  }
  
  return true;
}

function change_status( $uid, $newstatus ) {
  global $db;

  if ( $newstatus > 0 && $newstatus <= 3 ) {
    $sql = "update spkutil.user_request set status=" . $newstatus . " where id in (" . $uid . ")";
        
    if (DB::isError(  $res = $db->query($sql)) ) {
      // get the native backend error
      // and the last query
      die ($res->getDebugInfo());
      return false;
    }

    else {
      return true;
    }
  }
  else {
    // more error checking
    return false;
  }
}

// administrative functions begin here
function approve_user( $userlist ) {
  global $db;
  $db->setFetchMode(DB_FETCHMODE_OBJECT);
  // this assumes there is a list of at least one or more users to approve and 
  // that $userlist is an array.

  $users = "";
  if ( ! eregi("^[0-9,]+$", $userlist) ) {
    die("user list was invalid: $userlist");
  }

  $results = $db->query("select * from spkutil.user_request where id in ($userlist)");

  if ( $results->numRows() > 0 ) {
    while ( $results->fetchInto($row) ) {
      // this s where most of the work is done
      // get all the user information, store it in an object.
      if ( ! add_to_spk($row->first_name, $row->surname, $row->password, $row->username, $row->company, $row->country, $row->state, $row->email ) ) {
	die ("could not call add_to_spk($row)");
      }
      
      if ( !add_to_bugzilla($row->email, $row->first_name . " " . $row->surname, $row->password) ) {
	die ("Bugzilla add failed");
      }

      if ( ! change_status($row->id, 3) ) {
	die ("Could not change status to approved");
      }

      notify_customer_approved ( $row->username, $row->first_name, $row->surname, $row->email ); 
    }
  }
  else {
    return false;
  }
  return true;
}

function notify_customer_approved ( $username, $first_name, $surname, $email ) {
  
  require_once ("Mail.php");

  $headers['From'] = "Paolo Vicini <vicini@u.washington.edu>";
  //  $headers['From'] = "Andrew Ernst <ernst@u.washington.edu>";
  $headers['To'] = $first_name . " " . $surname . " <" . $email . ">";
  $headers['Subject'] = "Your new SPK account at RFPK";
  $headers['Bcc'] = $headers['From'];

  $message = 'Dear ' . $first_name . ',
Greetings. Thank you for your interest in SPK. As per your request, 
we have established a user account for you.

To access SPK, please direct your browser to the URL

            http://spk.rfpk.washington.edu

to get to the SPK web site.  Then select the

            mySPK

link and you will be asked to log in.

username: ' . $username . ' 

You have also been installed as a user of our Bugzilla system. Please use it to report bugs in the software or the documentation, or to suggest enhancements. The URL for Bugzilla is

       http://bugzilla.rfpk.washington.edu/index.cgi

Your authentication parameters are:

username: ' . $email . '

Please use the password you created when you requested your account.

If any of this does not work, please let me know, either by email or by phone. Please also make sure to familiarize yourself with our Terms of Service, available as a hyperlink on the left column of the MySPK page, at https://spk.rfpk.washington.edu/user/RFPK_SPK_TERMS_OF_SERVICE.html.

Questions about the user interface (the MDA), should be directed to Jiaji Du

email: jjdu@u.washington.edu

Please direct questions of a scientific or mathematical nature to David Salinger

email: salinger@u.washington.edu


We have developed a Getting Started document that should help you to develop your own models in SPK. It is available as a hyperlink on the login page.

At RFPK, we are all very excited about having "outside" users, and stand ready to assist you in any way that we can.

 

Best regards,

Paolo Vicini, Ph.D.
Associate Professor of Bioengineering

Mailing Address:
Resource Facility for Population Kinetics Department of Bioengineering, Box 355061 University of Washington Seattle, WA 98195-5061

Courier Address:
William H. Foege Building
1705 NE Pacific Street, Room N410G
University of Washington
Seattle, WA 98195-5061

Phone (206)616-1133

Fax (206)685-3300 (shared)

http://depts.washington.edu/bioe/people/core/vicini/vicini.html';

$mail_object =& Mail::factory('sendmail');
$mail_object->send($email, $headers, $message);

$mail_paolo =& Mail::factory('sendmail');
$mail_paolo->send($headers['From'], $headers, $message);

  return true;

}


function add_to_spk ( $first_name, $surname, $password, $username, $company, $country, $state, $email ) {
  global $db;

  $query = $db->prepare("insert into spkdb.user (first_name, surname, password, username, company, country, state, email) values (?, ?, md5(?), ?, ?, ?, ?, ?)");
  
  $newdata = array ( $first_name, $surname, $password, $username, $company, $country, $state, $email );
  
 
  if (DB::isError($res = $db->execute($query, $newdata)) ) {
    // get the native backend error
    // and the last query
    die ($res->getDebugInfo());
    return false;
  }

  return true;
}

function add_to_bugzilla ( $email_address, $name, $password ) {
  global $db;

  $query = $db->prepare("insert into bugs.profiles (login_name, cryptpassword, realname) values ( ?, encrypt(?), ? )");

  $data = array ($email_address, $password, $name);
  
  
  if (DB::isError($res = $db->execute($query, $data)) ) {
    // get the native backend error
    // and the last query
    die ($res->getDebugInfo());
    return false;
  }

  return true;
}

function authenticate_user ( $username, $password ) {
  global $db;

  $result = $db->query("SELECT username FROM spkdb.user where username='" . $username . "' and password=password('" . $password . "')");

  if ( $result->fetchInto($row) ) {
    if ( strcmp($row->username,$username) ) {
      return true;
    }
    else { 
      return false;
    }
  }
  else {
    return false;
  }
  return false;
}


// ------------------------------------------------------------
function list_events () {
  global $db;
  $text = '';
  $on = '';

  $result = $db->query("SELECT * FROM spkutil.events WHERE registraiton_status='open'");

  while ( $result->fetchInto($row) ) {
    $on = '';
    if ( $_SESSION['event_id'] == $row->id ) {
      $on = "CHECKED";
    }
    $text = $text . '<input name="event_id" type="radio" ' . $on . '  value="' . $row->id . '"><b><a href="' . $row->details . '">' . $row->name . "</a></b><br>&nbsp;&nbsp;&nbsp; ($" . $row->cost . ", <i> $" . $row->cost/2 . " registered students and postdocs)</i> <br />\n";
  }
  
  return $text;
}

// workshop status page

function change_registration_status( $eid, $pid, $newstatus ) {
  global $db;

  $query = $db->prepare ("UPDATE spkutil.registrations set registration_status=? WHERE event_id=? AND participant_id=?");
  
  $data = array ( $newstatus, $eid, $pid );
  
  if (DB::isError($res = $db->execute($query, $data)) ) {
    // get the native backend error
    // and the last query
    die ($res->getDebugInfo());
    return false;
  }

  return true;  
}

?>