//=============================================================
// 
// This file is generated by SPK Compiler.
// 
// The namespace NonmemPars exports the values 
// given by the user or determined by SPK Compiler based .
// upon them.
// 
// The user requested the population analysis.
// This means that this namespace would contain materials related to 
// all of THETA, OMEGA, ETA, SIGMA and EPS.
// 
//=============================================================
#ifndef NONMEMPARS_H
#define NONMEMPARS_H

#include <valarray>
#include <spkpred/PopPredModel.h>

namespace NonmemPars{
using namespace std;

   //-------------------------------------------
   // THETA
   //-------------------------------------------
   // The length of THETA vector.
   const int nTheta = 1;

   // A C-arrary containing the upper boundary values for THETA.
   // This array is used to initializes a valarray object that follows.
   double c_thetaUp[nTheta] = { 2   };
   const valarray<double> thetaUp ( c_thetaUp,  1 );

   // A C-arrary containing the lower boundary values for THETA.
   // This array is used to initializes a valarray object that follows.
   double c_thetaLow[nTheta] = { 0   };
   const valarray<double> thetaLow( c_thetaLow, 1 );
   // A C-arrary containing the initial estimates for THETA.
   // This array is used to initializes a valarray object that follows.
   double c_thetaIn[nTheta] = { 0.5 };

   // A valarray object containing the initial values for THETA.
   const valarray<double> thetaIn ( c_thetaIn, nTheta );

   //-------------------------------------------
   // OMEGA
   //-------------------------------------------
   // The structure of OMEGA matrix.
   // "FULL" indicates that possibly all elements of the symmetric matrix may be non-zero.
   // "DIAGONAL" indicates that only the diagonal elements are non-zero and the rest are all zero.
   const enum PopPredModel::covStruct omegaStruct = PopPredModel::DIAGONAL;

   // The order of OMEGA matrix.
   // If the matrix is full, the value is equal to the number of 
   // elements in a half triangle (diagonal elements included).
   // If the matrix is diagonal, it is equal to the dimension of the symmetric matrix.
   const int omegaOrder = 1;

   // A C-arrary containing the initial estimates for OMEGA.
   // This array is used to initializes a valarray object that follows.
   double c_omegaIn[ omegaOrder ] = { 1 };
   const valarray<double> omegaIn( c_omegaIn, omegaOrder );

   //-------------------------------------------
   // ETA
   //-------------------------------------------
   // The length of ETA vector, which is the order of OMEGA.
   const int nEta = 1;

   // A C-arrary containing the initial estimates for ETA.
   // This array is used to initializes a valarray object that follows.
   double c_etaIn[nEta] = { 0.0 };
   const valarray<double> etaIn( c_etaIn, nEta );

   //-------------------------------------------
   // SIGMA
   //-------------------------------------------
   const enum PopPredModel::covStruct sigmaStruct = PopPredModel::DIAGONAL;
   const int sigmaOrder = 1;
   double c_sigmaIn[ sigmaOrder ] = { 1 };
   const valarray<double> sigmaIn( c_sigmaIn, sigmaOrder );

   //-------------------------------------------
   // EPS
   //-------------------------------------------
   const int nEps = 1;

   //-------------------------------------------
   // Data Simulation
   //-------------------------------------------
// No simulation was requested.
// const int seed;

};
#endif