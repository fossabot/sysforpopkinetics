// THIS FILE IS GENERATED BY THE ASPK COMPILER
#ifndef PRED_H
#define PRED_H

#include <vector>
#include <string>
#include <spkpred/PredBase.h>
#include <CppAD/CppAD.h>
#include "DataSet.h"

const CppAD::AD<double> pow( const CppAD::AD<double>& x, int n )
{
   CppAD::AD<double> y = 1.0;
   if( n > 0 )
   {
      for( int i=0; i<n; i++ )
      {
         y *= x;
      }
   }
   else if( n < 0 )
   {
      for( int i=n; i<0; i++ )
      {
         y /= x;
      }
   }
   return y;
}
const CppAD::AD<double> pow( int x, const CppAD::AD<double>& n )
{
   return pow( static_cast< CppAD::AD<double> >( x ), n );
}
const CppAD::AD<double> pow( const CppAD::AD<double>& x, double n )
{
   return pow( x, CppAD::AD<double>( n ) );
}
const CppAD::AD<double> pow( double x, const CppAD::AD<double>& n )
{
   return pow( CppAD::AD<double>( x ), n );
}

template <class Value>
class Pred : public PredBase<Value>
{
public:
Pred( const DataSet<Value>* dataIn );
~Pred();
int getNObservs( int ) const;
bool eval( int spk_thetaOffset, int spk_thetaLen,
           int spk_etaOffset,   int spk_etaLen,
           int spk_epsOffset,   int spk_epsLen,
           int spk_fOffset,     int spk_fLen,
           int spk_yOffset,     int spk_yLen,
           int spk_i,
           int spk_j,
           const std::vector<Value>& spk_indepVar,
           std::vector<Value>& spk_depVar );

protected:
Pred();
Pred( const Pred& );
Pred & operator=( const Pred& );
private:
const int nIndividuals;
const DataSet<Value> *perm;
DataSet<Value> temp;
mutable bool isIterationCompleted;
mutable std::string ID;
mutable Value TIME;
mutable Value DV;
mutable Value MDV;
mutable Value F;
mutable Value PRED;
mutable Value RES;
mutable Value WRES;
mutable Value Y;
};
template <class Value>
Pred<Value>::Pred( const DataSet<Value>* dataIn )
: perm( dataIn ),
  nIndividuals( 2 ),
  isIterationCompleted( true )
{
}
template <class Value>
Pred<Value>::~Pred()
{
}
template <class Value>
int Pred<Value>::getNObservs( int spk_i ) const
{
  return perm->data[spk_i]->ID.size();
}
template <class Value>
bool Pred<Value>::eval( int spk_thetaOffset, int spk_thetaLen,
                        int spk_etaOffset,   int spk_etaLen,
                        int spk_epsOffset,   int spk_epsLen,
                        int spk_fOffset,     int spk_fLen,
                        int spk_yOffset,     int spk_yLen,
                        int spk_i,
                        int spk_j,
                        const std::vector<Value>& spk_indepVar,
                        std::vector<Value>& spk_depVar )
{
  assert( spk_thetaLen == 1 );
  assert( spk_etaLen   == 1 );
  assert( spk_epsLen   == 1 );

ID = perm->data[spk_i]->ID[spk_j];
TIME = perm->data[spk_i]->TIME[spk_j];
DV = perm->data[spk_i]->DV[spk_j];
MDV = perm->data[spk_i]->MDV[spk_j];
typename std::vector<Value>::const_iterator THETA1 = spk_indepVar.begin() + spk_thetaOffset + 0;
typename std::vector<Value>::const_iterator ETA1 = spk_indepVar.begin() + spk_etaOffset + 0;
typename std::vector<Value>::const_iterator EPS1 = spk_indepVar.begin() + spk_epsOffset + 0;
typename std::vector<Value>::const_iterator THETA = spk_indepVar.begin() + spk_thetaOffset;
typename std::vector<Value>::const_iterator ETA = spk_indepVar.begin() + spk_etaOffset;
typename std::vector<Value>::const_iterator EPS = spk_indepVar.begin() + spk_epsOffset;
Value F = 0.0;
Value Y = 0.0;
//=========================================
// Begin User Code                         
//-----------------------------------------

//  FIXED EFFECT THETA(1)  IS THE SLOPE OF THE LINE
//  RANDOM EFFECT ETA(1) IS THE INTERCEPT OF THE LINE
//  MEASUREMENT ERROR IS EPS(1)
// 
//  MODEL FOR THE MEAN GIVEN THE RANDOM EFFECTS
F = THETA[ ( 1 ) - 1 ] * TIME + ETA[ ( 1 ) - 1 ];
// 
//  MODEL FOR THE DATA FIVEN THE MEASUREMENT ERROR
Y = F + EPS[ ( 1 ) - 1 ];
//-----------------------------------------
// End User Code                           
//=========================================
PRED = F;
RES = perm->data[spk_i]->DV[spk_j] - PRED;
copy( EPS, EPS+spk_epsLen, temp.data[ spk_i ]->EPS[ spk_j ].begin() ); 
copy( ETA, ETA+spk_etaLen, temp.data[ spk_i ]->ETA[ spk_j ].begin() ); 
temp.data[ spk_i ]->F[ spk_j ] = F;
temp.data[ spk_i ]->PRED[ spk_j ] = PRED;
temp.data[ spk_i ]->RES[ spk_j ] = RES;
copy( THETA, THETA+spk_thetaLen, temp.data[ spk_i ]->THETA[ spk_j ].begin() ); 
temp.data[ spk_i ]->Y[ spk_j ] = Y;

if( spk_i == 2-1 && spk_j == perm->data[spk_i]->ID.size()-1 )
{
  // This means, SPK advanced in iteration.
  // Move temporary storage to permanent storage.
  isIterationCompleted = true;
  for( int i=0; i < nIndividuals; i++ )
  {
    perm->data[ i ]->EPS = temp.data[ i ]->EPS;
    perm->data[ i ]->ETA = temp.data[ i ]->ETA;
    perm->data[ i ]->F = temp.data[ i ]->F;
    perm->data[ i ]->PRED = temp.data[ i ]->PRED;
    perm->data[ i ]->RES = temp.data[ i ]->RES;
    perm->data[ i ]->THETA = temp.data[ i ]->THETA;
    perm->data[ i ]->WRES = temp.data[ i ]->WRES;
    perm->data[ i ]->Y = temp.data[ i ]->Y;
  }
}
else
{
  isIterationCompleted = false;
}

spk_depVar[ spk_fOffset+spk_j ] = F;
spk_depVar[ spk_yOffset+spk_j ] = Y;
if( perm->data[ spk_i ]->MDV[ spk_j ] == 0 )
   return true;
else return false;
}
template <class Value>
Pred<Value>::Pred()
{
}
template <class Value>
Pred<Value>::Pred( const Pred<Value>& )
{
}
template <class Value>
Pred<Value> & Pred<Value>::operator=( const Pred<Value>& )
{
}
#endif
