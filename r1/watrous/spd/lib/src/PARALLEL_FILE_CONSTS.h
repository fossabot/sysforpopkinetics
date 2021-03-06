/*
%************************************************************************
%                                                                       *
%  From:   Resource Facility for Population Kinetics                    *
%          Department of Bioengineering Box 352255                      *
%          University of Washington                                     *
%          Seattle, WA 98195-2255                                       *
%                                                                       *
%  Copyright (C) 2002, University of Washington,                        *
%  Resource Facility for Population Kinetics. All Rights Reserved.      *
%                                                                       *
%  This software was developed with support from NIH grant RR-12609.    *
%  Please cite this grant in any publication for which this software    *
%  is used and send a notification to the address given above.          *
%                                                                       *
%  Check for updates and notices at:                                    *
%  http://www.rfpk.washington.edu                                       *
%                                                                       *
%************************************************************************

*/
/*************************************************************************
 *
 * File: PARALLEL_FILE_CONSTS.cpp
 *
 *
 * Define a namespace containing constant values
 * used by Master and Node.
 *
 * Author: Sachiko Honda
 *
 *************************************************************************/

/*************************************************************************
 *
 * Namespace: parallel_const
 *
 *************************************************************************/

/*------------------------------------------------------------------------
 * Function Specification
 *------------------------------------------------------------------------*/

/*
$begin parallel_const$$
$spell 
  Spk
  const
  std
  ind
  vals
  vars
  namespace
$$

$section Namespace Defining Parallel Communication-specific Constant Values$$

$index parallel_const$$
$index parallel, constant values$$

$table
$bold Filename:$$   $cend  
$syntax/parallel_const/$$
$tend

See also: $xref/MasterEndChannel//MasterEndChannel/$$, 
$xref/NodeEndChannel//NodeEndChannel/$$.

$fend 20$$

$center
$italic
$include shortCopyright.txt$$
$$
$$
$pre
$$
$head Description$$
This namespace defines a set of constant values used
during the parallel communication.  Many of them
define parts of filenames created during the communication.

$head Values$$
$syntax/
const int MAX_NODES
/$$
defines the maximum number of nodes that can participate the computation.
Currently set to 999.
$syntax/

const int MAX_NODES_DIG
/$$
defines the maximum number of digits expressing $code MAX_NODES$$ in string.
$syntax/

const int MAX_INDS
/$$
defines the maximum number of individuals allowed in a population.
Currently set to 99999.
$syntax/

const int MAX_INDS_DIG
/$$
defines the maximum number of digits expressing $code MAX_INDS$$ in string.

$syntax/

const std::string SPK_EXCEPTION
/$$
defines the prefix of the name of file containing a 
$xref/SpkException//SpkException/$$ information.

$syntax/

const std::string SPK_POPCONSTS
/$$
defines the prefix of the name of file containing a 
$xref/PopConstVals//PopConstVals/$$ information.

$syntax/

const std::string SPK_POPVARS
/$$
defines the prefix of the name of file containing
a $xref/PopVars//PopVars/$$ information.

$syntax/

const std::string SPK_INDVARS
/$$
defines the prefix of the name of file containing
a $xref/IndVars//IndVars/$$ information.

$syntax/

const std::string SPK_INDRESULTS
/$$
defines the prefix of the name of file containing
a $xref/IndResults//IndResults/$$ information.

$syntax/

const std::string SPK_INDINPUT
/$$
defines the prefix of the name of file containing
a $xref/IndInputDataPackage//IndInputDataPackage/$$
information.

$syntax/

const std::string SPK_INDOUTPUT
/$$
defines the prefix of the name of file containing
a $xref/IndOutputDataPackage//IndOutputDataPackage/$$
information.

$syntax/

const std::string SPK_ESPK
/$$
defines the prefix of the name of file indicating
the end of Spk.

$syntax/

const std::string SPK_EPOP
/$$
defines the prefix of the name of file indicating
the end of an iteration of the population parameter optimization.

$syntax/

const std::string SPK_EIND
/$$
defines the prefix of the name of file indicating
the end of an iteration of the individual parameter optimization.

$syntax/

const std::string SPK_MASTER_SUFFIX
/$$
defines the file extension of files generated by Master.

$syntax/

const std::string SPK_NODE_SUFFIX
/$$
defines the file extension of files generated by Node.

$end
*/

#ifndef PARALLEL_FILE_CONSTS_H
#define PARALLEL_FILE_CONSTS_H

#include <string>
namespace parallel_const
{
    const int    MAX_NODES     = 999;
    const int    MAX_NODES_DIG = 3;
    const int    MAX_INDS      = 99999;
    const int    MAX_INDS_DIG  = 5;

    const std::string SPK_EXCEPTION("Spk_Exception_");
    const std::string SPK_POPCONSTS("Spk_PopConst_");
    const std::string SPK_POPVARS("Spk_PopVars_");
    const std::string SPK_INDVARS("Spk_IndVars_");
    const std::string SPK_INDRESULTS("Spk_IndResults_");
    const std::string SPK_INDINPUT("Spk_IndInData_");
    const std::string SPK_INDOUTPUT("Spk_IndOutData_");

    const std::string SPK_BEGIN("Spk_BeginSpk_");
    const std::string SPK_ESPK("Spk_EndSpk_");
    const std::string SPK_EPOP("Spk_EndPop_");
    const std::string SPK_EIND("Spk_EndInd_");

    const std::string SPK_MASTER_SUFFIX("master");
    const std::string SPK_NODE_SUFFIX("node");
}
#endif
