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
 * File: isEndOfSpk.cpp
 *
 *
 * isEndOfSpk() detects the end of Spk signal that may be raised by
 * Master.
 *
 * Author: Sachiko Honda
 *
 *************************************************************************/

/*************************************************************************
 *
 * Function: isEndOfSpk
 *
 *************************************************************************/

/*------------------------------------------------------------------------
 * Function Specification
 *------------------------------------------------------------------------*/

/*
$begin isEndOfSpk$$
$spell 
    spk const bool 
$$

$section Detects the End of a Spk Session$$

$index isEndOfSpk$$
$index parallel, end of SPK$$
$index signal, the end of Spk$$

$table
$bold Prototype:$$   $cend  
$syntax/bool isEndOfSpk(const File& /sharedDiskSpace/)
/$$
$tend

$fend 20$$

$center
$italic
$include shortCopyright.txt$$
$$
$$
$pre
$$
$head Description$$
$code isEndOfSpk$$ returns true if it detects the end of Spk signal that
may be raised by $bold Master$$.  The end signal is generated by 
$xref/broadCastEndOfSpk//broadCastEndOfSpk/$$ function.

$head Arguments$$
$syntax/
/sharedDiskSpace/
/$$
is a $xref/File//File/$$ object that absolutely points to a shared directory used
for Master and Node to communicate.
$end
*/

/*------------------------------------------------------------------------
 *
 * Implementation Notes
 * --------------------
 *
 * For Shared Disk Space version of parallel Spk, this is detecting a 
 * file of specific name.
 *
 *------------------------------------------------------------------------*/
#include <fstream>
#include "isEndOfSpk.h"
#include "File.h"
#include "PARALLEL_FILE_CONSTS.h"
#include "System.h"

bool isEndOfSpk(const File& sharedDiskSpace)
{
    using namespace parallel_const;
    bool isend = false;
    File endFile(sharedDiskSpace.getPath(), SPK_ESPK+"."+SPK_MASTER_SUFFIX);
    if( System::exist(endFile) )
        return true;
    else
        return false;

}
