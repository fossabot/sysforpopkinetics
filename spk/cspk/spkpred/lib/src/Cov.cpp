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
 * File: Cov.cpp
 *
 *
 * This is the abstract base class for covariance matrix classes.
 *
 * Covariance matrices must be positive definite and symmetric.
 *
 * Author: Mitch Watrous
 *
 *************************************************************************/

/*------------------------------------------------------------------------
 * Include files
 *------------------------------------------------------------------------*/

// SPK Pred library header files.
#include "Cov.h"
#include "isEqual.h"

// SPK library header files.
#include <spk/SpkValarray.h>

// Standard library header files.
#include <cassert>

using SPK_VA::valarray;


/*************************************************************************
 *
 * Function: Cov
 *
 *************************************************************************/

Cov::Cov( int nCovRowIn, int nParIn )
  :
  nCovRow          ( nCovRowIn ),
  nCov_parRow      ( nCovRowIn * nCovRowIn ),
  nPar             ( nParIn ),
  parCurr          ( nParIn ),
  covCurr          ( nCovRowIn * nCovRowIn ),
  cov_parCurr      ( nCovRowIn * nCovRowIn * nParIn ),
  invCurr          ( nCovRowIn * nCovRowIn ),
  inv_parCurr      ( nCovRowIn * nCovRowIn * nParIn ),
  isCovCurrOk      ( false ),
  isCov_parCurrOk  ( false ),
  isInvCurrOk      ( false ),
  isInv_parCurrOk  ( false )
{
}


/*************************************************************************
 *
 * Function: setPar
 *
 *************************************************************************/

void Cov::setPar( const valarray<double>& parIn )
{
  assert( parIn.size() == nPar );

  // Only reset the parameter value if it has changed.
  if ( !isEqual( parIn, parCurr ) )
  { 
    // If the parameter has changed, then any cached
    // values are no longer valid.
    invalidateCache();

    // Set the new value.
    parCurr = parIn;
  }
}


/*************************************************************************
 *
 * Function: invalidateCache
 *
 *************************************************************************/

void Cov::invalidateCache() const
{
  isCovCurrOk     = false;
  isCov_parCurrOk = false;
  isInvCurrOk     = false;
  isInv_parCurrOk = false;
}


/*************************************************************************
 *
 * Function: getUsedCachedCov
 *
 *************************************************************************/

bool Cov::getUsedCachedCov() const
{
  return usedCachedCov;
}


/*************************************************************************
 *
 * Function: getUsedCachedCov_par
 *
 *************************************************************************/

bool Cov::getUsedCachedCov_par() const
{
  return usedCachedCov_par;
}


/*************************************************************************
 *
 * Function: getUsedCachedInv
 *
 *************************************************************************/

bool Cov::getUsedCachedInv() const
{
  return usedCachedInv;
}


/*************************************************************************
 *
 * Function: getUsedCachedInv_par
 *
 *************************************************************************/

bool Cov::getUsedCachedInv_par() const
{
  return usedCachedInv_par;
}


/*************************************************************************
 *
 * Function: getNCovRow
 *
 *************************************************************************/

int Cov::getNCovRow() const
{
  return nCovRow;
}


/*************************************************************************
 *
 * Function: getNPar
 *
 *************************************************************************/

int Cov::getNPar() const
{
  return nPar;
}
