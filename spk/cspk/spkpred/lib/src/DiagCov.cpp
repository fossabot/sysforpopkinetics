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
 * File: DiagCov.cpp
 *
 *
 * This class supports diagonal covariance matrices.  It is a concrete 
 * subclass of the abstract covariance base class.
 *
 * This class utilizes the following parameterization for the covariance
 * matrix in order to insure that it is positive definite and symmetric:
 *
 *                    -                                                  -
 *                   |  exp[ 2 par  ]                             0       |
 *                   |            0                                       |
 *                   |                                                    |
 *                   |              exp[ 2 par  ]                         |
 *                   |                        1                           |
 *    cov( par )  =  |                      .                             |  ,
 *                   |                                                    |
 *                   |                         .                          |
 *                   |                            .                       |
 *                   |                                                    |
 *                   |                               exp[ 2 par       ]   |
 *                   |      0                                  nPar-1     |
 *                    -                                                -
 *
 * where par contains the current value for the parameters.
 *
 * Author: Mitch Watrous
 *
 *************************************************************************/

/*------------------------------------------------------------------------
 * Include files
 *------------------------------------------------------------------------*/

// SPK Pred library header files.
#include "DiagCov.h"

// SPK library header files.
#include <spk/SpkValarray.h>

// Standard library header files.
#include <cassert>
#include <cmath>

using SPK_VA::valarray;


/*************************************************************************
 *
 * Function: DiagCov
 *
 *************************************************************************/

DiagCov::DiagCov( int nCovRowIn )
  :
  Cov( nCovRowIn, nCovRowIn )
{
}


/*************************************************************************
 *
 * Function: cov
 *
 *
 * Evaluates the covariance matrix at the current parameter value.
 *
 *************************************************************************/

void DiagCov::cov( valarray<double>& covOut ) const
{
  //------------------------------------------------------------
  // Preliminaries.
  //------------------------------------------------------------

  covOut.resize( nCovRow * nCovRow );


  //------------------------------------------------------------
  // Use the cached value if possible.
  //------------------------------------------------------------

  if ( isCovCurrOk )
  {
    covOut = covCurr;
    usedCachedCov = true;

    return;
  }
  else
  {
    usedCachedCov = false;
  }


  //------------------------------------------------------------
  // Evaluate the covariance matrix.
  //------------------------------------------------------------

  // Create a matrix that has only zeroes.
  covOut = 0.0;

  // Set the diagonal elements,
  //
  //    cov      ( par )  =  exp[ 2 par  ]  .
  //       (i, i)                      i   
  //
  int i;
  for ( i = 0; i < nPar; i++ )
  {
    covCurr[i + i * nCovRow] = exp( 2.0 * parCurr[i] );
  }    


  //------------------------------------------------------------
  // Finish up.
  //------------------------------------------------------------

  isCovCurrOk = true;
  covOut = covCurr;
}


/*************************************************************************
 *
 * Function: cov_par
 *
 *
 * Evaluates the derivative of the covariance matrix at the current
 * parameter value.
 *
 *************************************************************************/

void DiagCov::cov_par( valarray<double>& cov_parOut ) const
{
  //------------------------------------------------------------
  // Preliminaries.
  //------------------------------------------------------------

  cov_parOut.resize( nCovRow * nCovRow * nPar );


  //------------------------------------------------------------
  // Use the cached value if possible.
  //------------------------------------------------------------

  if ( isCov_parCurrOk )
  {
    cov_parOut = cov_parCurr;
    usedCachedCov_par = true;

    return;
  }
  else
  {
    usedCachedCov_par = false;
  }


  //------------------------------------------------------------
  // Evaluate the derivative of the covariance matrix.
  //------------------------------------------------------------

  // Create a matrix that has only zeroes.
  cov_parOut = 0.0;

  // Set the nonzero elements of the derivative, i.e. the partial
  // derivatives of the diagonal elements of the covariance,
  //
  //     (i)     
  //    d     cov      ( par )  =  2 exp[ 2 par  ]  .
  //     par     (i, i)                        i
  //
  // Note that an rvec operation is performed on the elements of
  // the covariance before the partial derivatives are computed,
  //                                       -            -
  //                                      |              |
  //     d     cov( par )  =  d     rvec  |  cov( par )  |  .
  //      par                  par        |              |
  //                                       -            -
  int i;
  int row;
  for ( i = 0; i < nPar; i++ )
  {
    // Set the row in the rvec version of the covariance.
    row = i * nCovRow + i;

    cov_parCurr[row + i * nCov_parRow] = 2.0 * exp( 2.0 * parCurr[i] );
  }    


  //------------------------------------------------------------
  // Finish up.
  //------------------------------------------------------------

  isCov_parCurrOk = true;
  cov_parOut = cov_parCurr;
}


/*************************************************************************
 *
 * Function: inv
 *
 *
 * Evaluates the inverse of the covariance matrix at the current
 * parameter value,
 *
 *                                  -1
 *     inv( par )  =  [ cov( par ) ]    .
 * 
 *
 *************************************************************************/

void DiagCov::inv( valarray<double>& invOut ) const
{
  //------------------------------------------------------------
  // Preliminaries.
  //------------------------------------------------------------

  invOut.resize( nCovRow * nCovRow );


  //------------------------------------------------------------
  // Use the cached value if possible.
  //------------------------------------------------------------

  if ( isInvCurrOk )
  {
    invOut = invCurr;
    usedCachedInv = true;

    return;
  }
  else
  {
    usedCachedInv = false;
  }


  //------------------------------------------------------------
  // Evaluate the inverse of the covariance matrix.
  //------------------------------------------------------------

  // Create a matrix that has only zeroes.
  invOut = 0.0;

  // Set the diagonal elements,
  //
  //    inv      ( par )  =  exp[ - 2 par  ]  .
  //       (i, i)                        i
  //
  int i;
  for ( i = 0; i < nPar; i++ )
  {
    invCurr[i + i * nCovRow] = exp( -2.0 * parCurr[i] );
  }    


  //------------------------------------------------------------
  // Finish up.
  //------------------------------------------------------------

  isInvCurrOk = true;
  invOut = invCurr;
}


/*************************************************************************
 *
 * Function: inv_par
 *
 *
 * Evaluates the derivative of the inverse of the covariance matrix
 * at the current parameter value,
 *                                 -                   -
 *                                |                -1   |
 *     d     inv( par )  =  d     |  [ cov( par ) ]     |  .
 *      par                  par  |                     |
 *                                 -                   -
 *
 *************************************************************************/

void DiagCov::inv_par( valarray<double>& inv_parOut ) const
{
  //------------------------------------------------------------
  // Preliminaries.
  //------------------------------------------------------------

  inv_parOut.resize( nCovRow * nCovRow * nPar );


  //------------------------------------------------------------
  // Use the cached value if possible.
  //------------------------------------------------------------

  if ( isInv_parCurrOk )
  {
    inv_parOut = inv_parCurr;
    usedCachedInv_par = true;

    return;
  }
  else
  {
    usedCachedInv_par = false;
  }


  //------------------------------------------------------------
  // Evaluate the derivative of the inverse of the covariance matrix.
  //------------------------------------------------------------

  // Create a matrix that has only zeroes.
  inv_parOut = 0.0;

  // Set the nonzero elements of the derivative, i.e. the partial
  // derivatives of the diagonal elements of the inverse of the 
  // the covariance,
  //
  //     (i)     
  //    d     inv      ( par )  =  -2 exp[ -2 par  ]  .
  //     par     (i, i)                          i
  //
  // Note that an rvec operation is performed on the elements of
  // the covariance before the partial derivatives are computed,
  //                                       -            -
  //                                      |              |
  //     d     inv( par )  =  d     rvec  |  inv( par )  |  .
  //      par                  par        |              |
  //                                       -            -
  int i;
  int row;
  for ( i = 0; i < nPar; i++ )
  {
    // Set the row in the rvec version of the inverse of the covariance.
    row = i * nCovRow + i;

    inv_parCurr[row + i * nCov_parRow] = -2.0 * exp( -2.0 * parCurr[i] );
  }    


  //------------------------------------------------------------
  // Finish up.
  //------------------------------------------------------------

  isInv_parCurrOk = true;
  inv_parOut = inv_parCurr;
}


/*************************************************************************
 *
 * Function: getParLimits
 *
 *
 * Gets the lower and upper limits for the covariance matrix parameters
 * at the current parameter value.  These limits are for use during the
 * optimization of objective functions that depend on these parameters.
 *
 * This function assumes that the current values for the covariance
 * parameters are approximately equal to the final or true values.
 *
 *************************************************************************/

void DiagCov::getParLimits(
  valarray<double>&  parLow,
  valarray<double>&  parUp ) const
{
  //------------------------------------------------------------
  // Preliminaries.
  //------------------------------------------------------------

  parLow.resize( nPar );
  parUp .resize( nPar );


  //------------------------------------------------------------
  // Evaluate the current value for the covariance matrix. 
  //------------------------------------------------------------

  // If the covariance has not been evaluated at the current
  // value for the parameters, then evaluate it.
  if ( !isCovCurrOk )
  {
    valarray<double> tempCov( nCovRow * nCovRow );
    cov( tempCov );
  }


  //------------------------------------------------------------
  // Set the limits for the parameters.
  //------------------------------------------------------------

  // Set the limits for optimization to constrain the diagonal
  // elements of the covariance matrix as follows,
  //
  //      1      (curr)                              (curr)
  //     ---  cov        <=   cov        <=  100  cov        .
  //     100     (i,i)           (i,i)               (i,i) 
  //
  // These limits for the covariance diagonal elements implies
  // these limits for its parameters,
  //
  //              -                -                            -                -
  //             |                  |                          |                  |
  //      1      |   1      (curr)  |                   1      |          (curr)  |
  //     --- log |  ---  cov        |  <=   par    <=  --- log |  100  cov        |  .
  //      2      |  100     (i,i)   |          i        2      |          (i,i)   |
  //	         |  		    |                          |                  |
  //              -                -                            -                -
  //
  int i;
  for ( i = 0; i < nPar; i++ )
  {
    parLow[i] = 0.5 * log( covCurr[i + i * nCovRow] / 100.0 );
    parUp[i]  = 0.5 * log( covCurr[i + i * nCovRow] * 100.0 );
  }    

}
