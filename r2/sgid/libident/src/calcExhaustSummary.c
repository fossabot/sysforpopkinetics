/*************************************************************************
 *//**
 * @file calcExhaustSummary.c
 * 
 * 
 * Implements calcExhaustSummary() function.
 *//*
 * Author: Mitch Watrous
 *
 *************************************************************************/

/*------------------------------------------------------------------------
 * Include files
 *------------------------------------------------------------------------*/

// BLAD differential polynomial library header files.
#include <bad.h>

// CMP big number library header files.
#include <gmp.h>


/*************************************************************************
 *
 * Function: calcExhaustSummary
 *
 *//**
 * Attempts to calculate the exhaustive summary that corresponds to
 * the system-experiment model differential polyomial regular chain
 * sysExpModelRegChainIn.
 *
 * To be specific, this function attempts to calculate the exhaustive
 * summary polynomials that come from the input/output relations'
 * coefficients, which are evaluated at a random value for the vector
 * that will be determined to be identifiable or not, THETA.  See
 * Audoly et al. (2001) for details on the identifiability algorithm.
 *
 * Note that if the exhaustive summary is calculated successfully,
 * then memory will be allocated by this function for an array of C
 * strings containing the polynomials in the summary.  When these
 * polynomial strings are no longer needed, the memory required to
 * store them should be freed by the caller of this function.
 *
 *
 * Reference:
 *
 * S. Audoly, G. Bella, L. D'Angio, M. P. Saccomani, and C. Cobelli,
 * "Global Identifiability of Nonlinear Models of Biological Systems,"
 * IEEE Transactions on Biomedical Engineering, Vol. 48, pp. 55 - 65,
 * January 2001.
 *
 * F. Boulier, "The bad library (version 1.7.2)", pp. 2-6, 2004.
 *
 *
 * @param level
 * 
 * If level = 0, then no intermediate information from the
 * identifiability calculation will be printed to standard output.
 * If level = 1, then all of the intermediate information will be
 * printed except for the system-experiment model.
 * If level = 2, then all of the intermediate information will be
 * printed including the system-experiment model.
 * 
 * 
 * @param nTheta
 * 
 * The number of elements in the THETA vector, which are the parameters
 * that will be checked to be identifiable or not.
 * 
 * 
 * @param thetaName
 *
 * This array of strings contains the names for each element of the
 * THETA vector.
 *
 * Note that each element of THETA is allowed to have any name, not
 * just THETA1, THETA2, etc.
 *
 * For example, it could be that
 *
\verbatim
 
     thetaName = ( "K21", "K12", "V", "CL" )  .
 
\endverbatim
 *
 * or
 *
\verbatim
 
    thetaName = ( "THETA1", ... , "THETAR" )  ,
 
\endverbatim
 * where R = nTheta.
 *
 * 
 * @param thetaSeed
 * 
 * The value to use to seed the random number generator used to generate
 * the random value for THETA.
 * 
 * 
 * @param nIdentComp
 * 
 * The number of compartments that have ordinary differential equations
 * associated with them.
 * The masses in the compartments are functions of the time variable T
 * and are labeled A1, A2, ... , AP, where P = nIdentComp .
 *
 * 
 * @param nObservType
 * 
 * The number of observation types, i.e., the number of different data
 * streams that have measured values.
 * If there is more than one type of observation, then they will be
 * labeled Y1, Y2, ..., YV, in the differential polynomials that make
 * up the system-experiment model, where V = nObservType . If there is
 * only one observation type, then it will be simply labeled Y.
 *
 * 
 * @param nDoseType
 * 
 * The number of dose types, i.e., the number of different compartments
 * that will receive doses.
 * If there is more than one type of dose, then they will be labeled
 * U1, U2, ..., UQ , in the differential polynomials that make up the
 * system-experiment model, where Q = nDoseType . If there is only
 * dose type, then it will be simply labeled U.
 *
 * 
 * @param sysExpModelRegChainIn
 * 
 * This string is the sytem-experiment model regular chain, which
 * contains all of the differential polynomials that describe the
 * system and experiment.
 * An example sytem-experiment model regular chain is
\verbatim

    [A1[T] - THETA2*A2, A2[T] - THETA1*A1, Y1 - A1, THETA1[T], THETA2[T]]

\endverbatim
 * where
\verbatim
 
    thetaName = ( "THETA1", "THETA2" )  .
 
\endverbatim
 * The first and last characters of the string should be left and
 * right square brackets, respectively, and each of the differential
 * polynomials should be separted by a comma.
 * This function assumes that the ordering for the parameters that
 * appear in this regular chain is given by naturalOrderingIn.
 * A regular chain is defined as a triangular set of polynomials,
 * i.e., a set of polynomials having distinct leading variables.
 * Every triangular set of polynomials describes an ideal of
 * polynomials.  See the BLAD library documentation, Boulier (2004),
 * for more details on regular chains.
 * Note that in BLAD library notation, the derivatives with respect to
 * T are denoted
 * \f[
 *   \begin{array}{ccc}
 *     A1[T] = \partial / \partial T A1(T), & A1[T,T] = \partial / \partial T [ \partial / \partial T  A1(T) ], & \cdots \\
 *     A2[T] = \partial / \partial T A2(T), & A2[T,T] = \partial / \partial T [ \partial / \partial T  A2(T) ], & \cdots \\
 *           .             &         .                       &    .   \\
 *           .             &         .                       &    .   \\
 *           .             &         .                       &    .
 *     \mbox{}
 *   \end{array}
 * \f]
 * 
 * 
 * @param naturalOrderingIn
 * 
 * This string is the natural ordering for the variables, which is the
 * variable order that makes the set of the differential polynomials
 * for the system-experiment model be a regular chain.  It should be
 * of the form
\verbatim
 
    [[Y1, ... , YV, A1, ... , AP, U1, ... , UQ], [THETA1, ... , THETAR]]
 
\endverbatim
 * where
\verbatim
 
    thetaName = ( "THETA1", ..., "THETAR" )  ,
 
\endverbatim
 * V = nObservType, P = nIdentComp, Q = nDoseType, and R = nTheta.
 * If there is only one observation type, then it should be simply
 * labeled Y.
 * If there is only dose type, then it should be simply labeled U.
 * The first and last characters of the string should be left and
 * right square brackets, respectively.
 * 
 * @param charSetOrderingIn
 * 
 * This string is the characteristic set ordering for the variables,
 * which is the variable order that eliminates the compartment amounts
 * from the set of differential polynomials and leaves the
 * characteristic set.  It should be of the form
\verbatim

  [[A1, ... , AP], [Y1, ... , YV, U1, ... , UQ], [THETA1, ..., THETAR]]

\endverbatim
 * where
\verbatim
 
    thetaName = ( "THETA1", ..., "THETAR" )  ,
 
\endverbatim
 * V = nObservType, P = nIdentComp, Q = nDoseType, and R = nTheta.
 * If there is only one observation type, then it should be simply
 * labeled Y.
 * If there is only dose type, then it should be simply labeled U.
 * The first and last characters of the string should be left and
 * right square brackets, respectively.
 * 
 * @param exhaustSummaryPolyOut
 * 
 * If the exhaustive summary was calculated successfully, then the
 * memory pointed to by this pointer to a pointer to a char pointer
 * (char***) will contain an array with separate C strings for each of
 * the polynomials in the summary.  An example exhaustive summary
 * polynomial is
 * \verbatim

    THETA1*THETA2 - 28

\endverbatim
 * where
\verbatim
 
    thetaName = ( "THETA1", "THETA2" )  .
 
\endverbatim
 * When these polynomials are no longer needed, the memory required to
 * store them should be released by the caller of this function.  No
 * memory should be allocated by the caller before calling this
 * function, however, because this function does the allocation.  It
 * is the responsibility of the caller of the function to free the
 * memory allocated by this function.  The memory pointed to by
 * exhaustSummaryOut will be allocated using malloc() as a C array of
 * nExhaustSummPoly pointers to C style strings, each of which
 * contains a polynomial from the exhaustive summary and each of which
 * will be allocated using malloc() with enough memory to hold the
 * polynomial. The value for nExhaustSummPoly is equal to the
 * number of polynomials in the exhaustive summary and is returned by
 * this function.  The following code shows how to get a pointer to
 * the C string that contains the m-th polynomial:
 * \code
 *
 *     char* poly_m = (*exhaustSummaryPolyOut)[m];
 *
 * \endcode
 * When the polynomials are no longer needed, then the memory for each
 * C string must be freed using free() and the memory for the array of
 * pointers to the polynomials' C strings must be freed also using
 * free().
 * The following code shows how to free all of the memory allocated by
 * this function to hold the exhaustive summary polynomials:
 * \code
 *
 *   for ( m = 0; m < nExhaustSummPoly; m++)
 *   {
 *     // Free the memory for this polynomial's C style string.
 *     free( (*exhaustSummaryPolyOut)[m] );
 *   }
 * 
 *   // Free the memory for the pointers to the C style strings.
 *   free( (*exhaustSummaryPolyOut) );
 *
 * \endcode
 * The above code should be executed by the caller of this function.
 *
 *
 * @return
 *
 * The return value will be the number of exhaustive summary
 * polynomials nExhaustSummPoly.
 *
 * If the return value is equal to -1, then the exhaustive summary
 * polynomials were not calculated because the system-experiment model
 * is not algebraically observable.  In this case the memory pointed
 * to by exhaustSummaryPolyOut will not be allocated.
 *
 * If the return value is equal to -2, then the exhaustive summary
 * polynomials were not calculated because an error occurred during
 * a call to one of the BLAD library functions.  In this case the
 * memory pointed to by exhaustSummaryPolyOut will not be allocated.
 */
/*************************************************************************/

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// [Revisit - Duplicated Code - Mitch]
//
// The code that calculates the exhaustive summary in this function
// is duplicated in calcExhaustSummary.c.  Changes to this function
// should be duplicated there to keep that function up to date.
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

int calcExhaustSummary( int         level,
                        int         nTheta,
                        char**      thetaName,
                        int         thetaSeed,
                        int         nIdentComp,
                        int         nObservType,
                        int         nDoseType,
                        const char* sysExpModelRegChainIn,
                        const char* naturalOrderingIn,
                        const char* charSetOrderingIn,
                        char***     exhaustSummaryPolyOut )
{
  //----------------------------------------------------------
  // Preliminaries.
  //----------------------------------------------------------

  // Call the BLAD library initialization function with a 30 minute
  // time limit and a limit on the memory that can be used during the
  // calculations.
  ba0_int_p timeLimit   = 30 * 60;  // Seconds.
  ba0_int_p memoryLimit = 1000;     // Megabytes.
  bad_restart( timeLimit, memoryLimit );

  // This is a marker used by the BLAD library to manage memory
  // dynamically.
  struct ba0_mark memoryMarker;

  // This function records the value of the free pointer of the
  // current stack.
  ba0_record( &memoryMarker );


  //----------------------------------------------------------
  // Set the natural ordering for the variables.
  //----------------------------------------------------------

  bav_Iordering naturalOrdering;

  int nNaturalOrderingStringChar = strlen( naturalOrderingIn ) + 100;

  // Allocate more than enough memory for the natural ordering string
  // with all of its BLAD library qualifiers.
  char* naturalOrderingString = 
    (char*) malloc( nNaturalOrderingStringChar * sizeof( char ) );

  // Set the string using snprintf() to make sure the allocated buffer
  // is not overwritten.
  snprintf(
    naturalOrderingString,
    nNaturalOrderingStringChar,
    "ordering( derivations = [T], blocks = %s )",
    naturalOrderingIn );

  // Set the natural ordering.
  ba0_sscanf2(
    naturalOrderingString,
    "%ordering",
    &naturalOrdering );

  // Add the natural ordering to the BLAD global variable bav_R, which
  // Add the natural ordering to the BLAD global variable bav_R, which
  // contains information of the defined orderings and variables.
  bav_R_push_ordering( naturalOrdering );

  // Free the string.
  free( naturalOrderingString );


  //----------------------------------------------------------
  // Set the sytem-experiment model regular chain.
  //----------------------------------------------------------

  // The sytem-experiment model regular chain contains all of the
  // differential polynomials that describe the system and experiment.
  struct bad_regchain sysExpModelRegChain;
  bad_init_regchain( &sysExpModelRegChain );

  int nSysExpModelRegChainStringChar = strlen(sysExpModelRegChainIn ) + 100;

  // Allocate more than enough memory for the sytem-experiment model
  // regular chain string with all of its BLAD library qualifiers.
  char* sysExpModelRegChainString = 
    (char*) malloc( nSysExpModelRegChainStringChar * sizeof( char ) );

  // Set the string using snprintf() to make sure the allocated buffer
  // is not overwritten.
  snprintf(
   sysExpModelRegChainString,
    nSysExpModelRegChainStringChar,
    "regchain( %s, [prime, differential, autoreduced, squarefree, primitive] )",
    sysExpModelRegChainIn );

  // Initialize a BLAD library exception handling structure and
  // set a catch point for the long jump associated with it.
  struct ba0_exception_code excepHandlerSysExpModel;
  BA0_PUSH_EXCEPTION( excepHandlerSysExpModel );

  // Set the sytem-experiment model regular chain.
  //
  // The first time this if block is executed ba0_exception_is_set()
  // will return true.  The long jump will return here if an exception
  // is raised and then ba0_exception_is_set() will return false.
  if ( ba0_exception_is_set( excepHandlerSysExpModel ) )
  {
    ba0_sscanf2(
      sysExpModelRegChainString,
      "%regchain",
      &sysExpModelRegChain );

    // Remove this exception catching point.
    ba0_pull_exception( excepHandlerSysExpModel );
  }
  else
  {
    if ( level > 0 )
    {
      printf( "The identifiability calculation failed while setting the system-experiment model \n" );

      // Check to see what type of error occurred.
      if ( ba0_mesgerr == BA0_ERROOM )
      {
        printf( "because too much computer memory was used. \n" );
      }
      else if ( ba0_mesgerr == BA0_ERRALR )
      {
        printf( "because it could not be completed in less than 30 minutes. \n" );
      }
      else if ( ba0_mesgerr == BA0_ERRALG )
      {
        printf( "because a BLAD library internal error occurred. \n" );
        printf( "\n" );
        printf( "Please submit a bug report.\n" );
      }
      else
      {
        printf( "because of an unknown error.\n" );
        printf( "\n" );
        printf( "Please submit a bug report.\n" );
      }
      printf( "\n" );
    }

    // Call the BLAD library termination function.
    bad_terminate( ba0_init_level );

    // Return -2 as the number of exhaustive summary polynomials 
    // to indicate that a BLAD error occurred.
    return -2;
  }    

  // Free the string.
  free( sysExpModelRegChainString );

  int r;

  // Set the number of polynomials in the system-experiment model.
  int nSysExpModelPoly = sysExpModelRegChain.decision_system.size;

  // Print the polynomials in the system-experiment model.
  if ( level > 1 )
  {
    printf( "System-experiment model = {\n" );
    printf( "\n" );
    for ( r = 0; r < nSysExpModelPoly; r++ )
    {
      // Reset the output driver to avoid spurious line breaks.
      ba0_reset_output();

      ba0_printf( "%Az", sysExpModelRegChain.decision_system.tab[r] );

      if ( r < nSysExpModelPoly - 1 )
      {
        printf( ",\n" );
      }
      else
      {
        printf( " }\n" );
      }
      printf( "\n" );
    }
    printf( "\n" );
  }


  //----------------------------------------------------------
  // Set the variable ordering used to get the characteristic set.
  //----------------------------------------------------------

  bav_Iordering charSetOrdering;

  int nCharSetOrderingStringChar = strlen( charSetOrderingIn ) + 100;

  // Allocate more than enough memory for the characteristic set
  // ordering string with all of its BLAD library qualifiers.
  char* charSetOrderingString = 
    (char*) malloc( nCharSetOrderingStringChar * sizeof( char ) );

  // Set the string using snprintf() to make sure the allocated buffer
  // is not overwritten.
  snprintf(
    charSetOrderingString,
    nCharSetOrderingStringChar,
    "ordering( derivations = [T], blocks = %s )",
    charSetOrderingIn );

  // Set the characteristic set ordering.
  ba0_sscanf2(
    charSetOrderingString,
    "%ordering",
    &charSetOrdering );

  // Free the string.
  free( charSetOrderingString );


  //----------------------------------------------------------
  // Get the characteristic set.
  //----------------------------------------------------------

  struct bad_regchain charSetRegChain;

  bad_init_regchain( &charSetRegChain );

  ba0_sscanf2( "regchain( [], [autoreduced, squarefree, primitive, normalized])",
               "%regchain", &charSetRegChain );

  // Initialize a BLAD library exception handling structure and
  // set a catch point for the long jump associated with it.
  struct ba0_exception_code excepHandlerCharSet;
  BA0_PUSH_EXCEPTION( excepHandlerCharSet );

  // Change the ordering for the variables in order to get the
  // characteristic set.
  //
  // The first time this if block is executed ba0_exception_is_set()
  // will return true.  The long jump will return here if an exception
  // is raised and then ba0_exception_is_set() will return false.
  if ( ba0_exception_is_set( excepHandlerCharSet ) )
  {
    bad_pardi( &charSetRegChain, charSetOrdering, &sysExpModelRegChain );

    // Remove this exception catching point.
    ba0_pull_exception( excepHandlerCharSet );
  }
  else
  {
    if ( level > 0 )
    {
      printf( "The identifiability calculation failed while calculating the characteristic set \n" );

      // Check to see what type of error occurred.
      if ( ba0_mesgerr == BA0_ERROOM )
      {
        printf( "because too much computer memory was used. \n" );
      }
      else if ( ba0_mesgerr == BA0_ERRALR )
      {
        printf( "because it could not be completed in less than 30 minutes. \n" );
      }
      else if ( ba0_mesgerr == BA0_ERRALG )
      {
        printf( "because a BLAD library internal error occurred. \n" );
        printf( "\n" );
        printf( "Please submit a bug report.\n" );
      }
      else
      {
        printf( "because of an unknown error.\n" );
        printf( "\n" );
        printf( "Please submit a bug report.\n" );
      }
      printf( "\n" );
    }

    // Call the BLAD library termination function.
    bad_terminate( ba0_init_level );

    // Return -2 as the number of exhaustive summary polynomials 
    // to indicate that a BLAD error occurred.
    return -2;
  }    

  // Set the number of polynomials in the characteristic set.
  int nCharSetPoly = charSetRegChain.decision_system.size;

  int k;

  // Print the polynomials in the characteristic set.
  if ( level > 0 )
  {
    printf( "Characteristic set = {\n" );
    printf( "\n" );
    for ( k = 0; k < nCharSetPoly; k++ )
    {
      // Reset the output driver to avoid spurious line breaks.
      ba0_reset_output();

      ba0_printf( "%Az", charSetRegChain.decision_system.tab[k] );

      if ( k < nCharSetPoly - 1 )
      {
        printf( ",\n" );
      }
      else
      {
        printf( " }\n" );
      }
      printf( "\n" );
    }
    printf( "\n" );
  }


  //----------------------------------------------------------
  // Determine if the model is algebraically observable.
  //----------------------------------------------------------

  int nCompDerivStringChar = 20;

  // Allocate more than enough memory for the string used to look 
  // for the compartments' time derivatives.
  char* compDerivString_p = 
    (char*) malloc( nCompDerivStringChar * sizeof( char ) );

  bav_variable compDerivVar_p;

  int p;

  // Determine if this system-experiment model is not algebraically
  // observable by looking for the occurrence of any order time
  // derivative of any compartment amount, i.e.
  //
  //     A1[T], A1[T,T], A1[T,T,T], ... ,
  //     A2[T], A2[T,T], A2[T,T,T], ... ,
  //       .  ,   .    ,   .      ,  .  ,
  //       .  ,   .    ,   .      ,  .  ,
  //       .  ,   .    ,   .      ,  .  ,
  //
  // in any of the polynomials in the characteristic set.
  for ( k = 0; k < nCharSetPoly; k++ )
  {
    // Look in this characteristic set polynomial for the first 
    // order time derivative of any compartment amount, i.e.,
    //
    //     A1[T], A2[T], A3[T], ... , AP[T]  ,
    //
    // where P = nIdentComp.
    //
    // Note:  this code currently assumes that any higher order 
    // derivatives will be accompanied by a first order derivative
    // in at least one of the characteristic set polynomials.
    for ( p = 0; p < nIdentComp; p++ )
    {
      // Set this compartment amount first order derivative string
      // using snprintf() to make sure the allocated buffer is not
      // overwritten.
      snprintf(
        compDerivString_p,
        nCompDerivStringChar,
        "A%d[T]",
        p + 1 );

      // Set this compartment amount first order derivative.
      ba0_sscanf2( compDerivString_p, "%v", &compDerivVar_p );

      // Look for any dependence on the first order time derivative
      // in this characteristic set polynomial.
      if ( bap_depend_polynom_mpz(
             charSetRegChain.decision_system.tab[k],
             compDerivVar_p ) )
      {
        // Undo the last call to bav_R_push_ordering().
        bav_R_pull_ordering();
        
        // This function restores the value of the free pointer.
        ba0_restore( &memoryMarker );
        
        // Call the BLAD library termination function.
        bad_terminate( ba0_init_level );
  
        // Return -1 as the number of exhaustive summary polynomials 
        // to indicate that the exhaustive summary was not calculated
        // because the system is not algebraically observable.
        return -1;
      }
    }
  }

  // Free the string.
  free( compDerivString_p );


  //----------------------------------------------------------
  // Set the variables for the I/O relations' polynomials.
  //----------------------------------------------------------

  bav_variable lastVar;

  int nLastVarChar = 20;
  char lastVarString[nLastVarChar];

  // With the new ordering the I/O relations appear after the
  // differential polynonimials for each of the parameters, which do
  // not change in time.
  //
  // There is one I/O relation for each type of observation, i.e. for
  // each data stream that has measured values.
  //
  // The terms for the I/O relations are polynomials in the variables
  // up to the lastVar variable in the characteristic set ordering.
  //
  // So, set lastVar equal to the variable that comes just before
  // THETA1 in the ordering.
  if ( nDoseType == 1 )
  {
    // If there is only one dose type, then set the lastVar
    // variable equal to
    //
    //     U  .
    //
    ba0_sscanf2( "U", "%v", &lastVar );
  }
  else if ( nDoseType > 0 )
  {
    // If there is more than one dose type, then set the lastVar
    // variable equal to
    //
    //     UQ  ,
    //
    // where Q = nDoseType.
    snprintf( lastVarString, nLastVarChar, "U%i", nDoseType );

    ba0_sscanf2( lastVarString, "%v", &lastVar );
  }
  else
  {
    // If there is no dose type, then set the lastVar variable equal
    // to
    //
    //     AP  ,
    //
    // where  P = nIdentComp.
    snprintf( lastVarString, nLastVarChar, "A%i", nIdentComp );

    ba0_sscanf2( lastVarString, "%v", &lastVar );
  }


  //----------------------------------------------------------
  // Set a random value for the exhaustive summary parameters.
  //----------------------------------------------------------

  struct bav_ev_point inOutRelPolyCoeffEvalPoint;
  bav_init_ev_point( &inOutRelPolyCoeffEvalPoint );
  bav_realloc_ev_point( &inOutRelPolyCoeffEvalPoint, nTheta );

  // Initialize the random number generator state variable.
  gmp_randstate_t randomNumberState;
  gmp_randinit_default( randomNumberState );

  // Set the seed value for the random number generator.
  gmp_randseed_ui( randomNumberState, thetaSeed );

  mpz_t thetaRandom_r;
  mpz_t thetaRandom_rStartsAtZero;
  mpz_t thetaRandomMaxArg;

  mpz_init( thetaRandom_r  );
  mpz_init( thetaRandom_rStartsAtZero );
  mpz_init( thetaRandomMaxArg );

  // Set the maximum random theta value and the argument that controls
  // the upper bound for the random number generator.
  int thetaRandomMax = 30;
  mpz_set_si( thetaRandomMaxArg, thetaRandomMax );

  int nEvalPointFormatChar = 50;

  // Allocate more than enough memory for the evaluation point format
  // string.
  char* evalPointFormatString_r = 
    (char*) malloc( nEvalPointFormatChar * sizeof( char ) );

  // Choose a random value for the parameters that appear in the
  // exhaustive summary.
  for ( r = 0; r < nTheta; r++)
  {
    // Generate a uniform random integer in the range
    //
    //     [0, thetaRandomMax - 1]  ,
    //
    mpz_urandomm( thetaRandom_rStartsAtZero, randomNumberState, thetaRandomMaxArg );

    // Add one to that number so that it is in the range
    //
    //     [1, thetaRandomMax]  ,
    //
    mpz_add_ui( thetaRandom_r, thetaRandom_rStartsAtZero, 1 );

    // Prepare the format string for setting this element of the
    // random THETA value.
    gmp_snprintf(
      evalPointFormatString_r,
      nEvalPointFormatChar,
      "%s = %Zd",
      thetaName[r],
      thetaRandom_r );

    // Set this element of the random THETA value.
    ba0_sscanf2(
      evalPointFormatString_r,
      "%v = %d",
      &inOutRelPolyCoeffEvalPoint.tab[r].var,
      &inOutRelPolyCoeffEvalPoint.tab[r].value );
  }

  // Reset the size to account for the elements that were just added
  // to the table.
  inOutRelPolyCoeffEvalPoint.size = nTheta;

  // Free the string.
  free( evalPointFormatString_r );

  // Print the random THETA vector.
  if ( level > 0 )
  {
    printf( "Random parameter value = { " );

    // Reset the output driver to avoid spurious line breaks.
    ba0_reset_output();

    for ( r = 0; r < nTheta; r++ )
    {
      ba0_printf( "%d", inOutRelPolyCoeffEvalPoint.tab[r].value );

      if ( r < nTheta - 1 )
      {
        printf( ", " );
      }
      else
      {
        printf( " }\n" );
      }
    }
    printf( "\n" );
    printf( "\n" );
  }


  //----------------------------------------------------------
  // Prepare to get the exhaustive summary.
  //----------------------------------------------------------

  struct bap_itercoeff_mpz inOutRelPolyCoeffIter;
  struct bap_polynom_mpz   inOutRelPolyCoeff;
  struct bav_term          inOutRelPolyTerm;

  bap_init_readonly_polynom_mpz( &inOutRelPolyCoeff );
  bav_init_term( &inOutRelPolyTerm );

  struct bap_polynom_mpz inOutRelPolyCoeffValTimesLeadingCoeff;
  struct bap_polynom_mpz inOutRelPolyCoeffValTimesLeadingCoeffReduced;
  struct bap_polynom_mpz inOutRelPolyLeadingCoeff;
  struct bap_polynom_mpz inOutRelPolyLeadingCoeffValTimesCoeff;
  struct bap_polynom_mpz inOutRelPolyLeadingCoeffValTimesCoeffReduced;
  struct bap_polynom_mpz inOutRelPolyTermsForExhaustSummGCD;

  bap_init_polynom_mpz( &inOutRelPolyCoeffValTimesLeadingCoeff );
  bap_init_polynom_mpz( &inOutRelPolyCoeffValTimesLeadingCoeffReduced );
  bap_init_polynom_mpz( &inOutRelPolyLeadingCoeff );
  bap_init_polynom_mpz( &inOutRelPolyLeadingCoeffValTimesCoeff );
  bap_init_polynom_mpz( &inOutRelPolyLeadingCoeffValTimesCoeffReduced );
  bap_init_polynom_mpz( &inOutRelPolyTermsForExhaustSummGCD );

  mpz_t inOutRelPolyCoeffVal;
  mpz_t inOutRelPolyLeadingCoeffVal;

  mpz_init( inOutRelPolyCoeffVal );
  mpz_init( inOutRelPolyLeadingCoeffVal );

  bap_tableof_polynom_mpz exhaustSumm;
  exhaustSumm =( bap_tableof_polynom_mpz)ba0_new_table();
  int nExhaustSummPoly = 0;

  struct bap_polynom_mpz exhaustSummPoly;
  bap_init_polynom_mpz( &exhaustSummPoly );


  //----------------------------------------------------------
  // Get the exhaustive summary.
  //----------------------------------------------------------

  int v;

  // Get the polynomials for the exhaustive summary that correspond
  // to each of the monomials from the I/O relations.
  //
  // There is one I/O relation for each type of observation, i.e. for
  // each data stream that has measured values.
  for ( v = 0; v < nObservType; v++ )
  {
    // Get an iterator over the coefficents of the differential
    // polynomial for this observation type's I/O relation.
    bap_begin_itercoeff_mpz(
      &inOutRelPolyCoeffIter, 
      charSetRegChain.decision_system.tab[nTheta + v], lastVar );

    // Get the coefficient for the first monomial in this I/O
    // relation's polynomial.
    bap_coeff_itercoeff_mpz( &inOutRelPolyLeadingCoeff, &inOutRelPolyCoeffIter );

    // Get the value of the first monomial's coefficient.
    bap_eval_polynom_ev_point_mpz(
      &inOutRelPolyLeadingCoeffVal,
      &inOutRelPolyLeadingCoeff,
      &inOutRelPolyCoeffEvalPoint );

    // Since all of the coefficients of the I/O relation's polynomial
    // are scaled by the first monomial's coefficent, the first
    // momonial's scaled coefficent will just be equal to one and
    // won't be included in the exhaustive summary.
    //
    // Therefore, get the next monomial in this I/O relation's
    // polynomial.
    bap_next_itercoeff_mpz( &inOutRelPolyCoeffIter );

    // Get the exhaustive summary terms that correspond to the rest of
    // the monomials in this I/O relation's polynomial.
    while ( !bap_outof_itercoeff_mpz( &inOutRelPolyCoeffIter ) )
    {
      // Get the term and coefficient.
      bap_term_itercoeff_mpz( &inOutRelPolyTerm, &inOutRelPolyCoeffIter );
      bap_coeff_itercoeff_mpz( &inOutRelPolyCoeff, &inOutRelPolyCoeffIter );
  
      // Get the value of the unscaled coefficient.
      bap_eval_polynom_ev_point_mpz(
        &inOutRelPolyCoeffVal,
        &inOutRelPolyCoeff,
        &inOutRelPolyCoeffEvalPoint );

      // Calculate
      //
      //                         |                                              
      //         coeff ( THETA ) |                      *  coeff ( THETA )  .
      //              1          | THETA = thetaRandom          k            
      //
      bap_mul_polynom_numeric_mpz( 
        &inOutRelPolyLeadingCoeffValTimesCoeff,
        &inOutRelPolyCoeff,
        inOutRelPolyLeadingCoeffVal );

      // Calculate
      //
      //                     |                                              
      //     coeff ( THETA ) |                      *  coeff ( THETA )  .
      //          k          | THETA = thetaRandom          1            
      //
      bap_mul_polynom_numeric_mpz( 
        &inOutRelPolyCoeffValTimesLeadingCoeff,
        &inOutRelPolyLeadingCoeff,
        inOutRelPolyCoeffVal );

      // Reduce the previous two polynomials that were just calculated
      // by dividing them by their greatest common denominator (GCD).
      bap_gcd_polynom_mpz( 
        &inOutRelPolyTermsForExhaustSummGCD,
        &inOutRelPolyLeadingCoeffValTimesCoeffReduced,
        &inOutRelPolyCoeffValTimesLeadingCoeffReduced,
        &inOutRelPolyLeadingCoeffValTimesCoeff,
        &inOutRelPolyCoeffValTimesLeadingCoeff );

      // Set the exhaustive summary polynomial,
      //
      //     exhaustSummPoly  =
      //
      //                 -
      //           1    |                 |                                              
      //         -----  | coeff ( THETA ) |                      *  coeff ( THETA )
      //          GCD   |      1          | THETA = thetaRandom          k            
      //                 -
      //                                                                                 -
      //                                        |                                         |     
      //                     -  coeff ( THETA ) |                      *  coeff ( THETA ) | .
      //                             k          | THETA = thetaRandom          1          |  
      //                                                                                 -
      //
      // where GCD is the greatest common denominator for the two
      // terms that are being subtracted.
      bap_sub_polynom_mpz( 
        &exhaustSummPoly,
        &inOutRelPolyLeadingCoeffValTimesCoeffReduced,
        &inOutRelPolyCoeffValTimesLeadingCoeffReduced );

      // Set the value for this exhaustive summary polynomial in the
      // table.
      //
      // Only add this polynomial to the exhaustive summary if it
      // depends on the parameters.
      if ( !bap_is_zero_polynom_mpz   ( &exhaustSummPoly ) &&
           !bap_is_one_polynom_mpz    ( &exhaustSummPoly ) &&
           !bap_is_numeric_polynom_mpz( &exhaustSummPoly ) )
      {
        // Prepare the element in the table that will get this
        // polynomial.
        nExhaustSummPoly++;
        ba0_realloc_table( (ba0_table)exhaustSumm, nExhaustSummPoly );
        exhaustSumm->size = nExhaustSummPoly;
        exhaustSumm->tab[nExhaustSummPoly-1] = bap_new_polynom_mpz();

        bap_set_polynom_mpz(
          exhaustSumm->tab[nExhaustSummPoly-1],
          &exhaustSummPoly );
      }

      // Get the next monomial in the I/O relation's differential
      // polynomial.
      bap_next_itercoeff_mpz( &inOutRelPolyCoeffIter );
    }
  }

  bap_close_itercoeff_mpz( &inOutRelPolyCoeffIter );
 
  int i;

  // Print the polynomials in the exhaustive summary.
  if ( level > 0 )
  {
    if ( nExhaustSummPoly == 0 )
    {
      printf( "Exhaustive summary = { }\n" );
      printf( "\n" );
    }
    else
    {
      printf( "Exhaustive summary = {\n" );
      printf( "\n" );
      for ( i = 0; i < nExhaustSummPoly; i++ )
      {
        // Reset the output driver to avoid spurious line breaks.
        ba0_reset_output();
      
        ba0_printf( "%Az", exhaustSumm->tab[i] );
      
        if ( i < nExhaustSummPoly - 1 )
        {
          printf( ",\n" );
        }
        else
        {
          printf( " }\n" );
        }
        printf( "\n" );
      }
    }
    printf( "\n" );
  }


  //----------------------------------------------------------
  // Set the exhaustive summary polynomial strings.
  //----------------------------------------------------------

  // Allocate enough elements in the output array to hold all of the
  // of polynomials in the exhaustive summary.
  *exhaustSummaryPolyOut = 
      (char**) malloc( nExhaustSummPoly * sizeof( char* ) );

  char* exhaustSummaryPolyString_m;
  int nExhaustSummPolyChar_m;

  int m;

  // Set the output array of exhaustive summary polynomial strings.
  for ( m = 0; m < nExhaustSummPoly; m++ )
  {
    // Get a string that contains this exhaustive summary polynomial.
    //
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // [Revisit - Possible BLAD Library Memory Leak - Mitch]
    // Note that this string is allocated by the BLAD library function
    // ba0_malloc and it is assumed that the BLAD memory clean up
    // functions will free this memory.
    //
    // Even if the memory does not get freed, it is probably not a big
    // problem since the calculation of the exhaustive summary happens
    // only once during the identifiability calculation, and the
    // amount of memory for the summary polynomials is relatively small.
    //
    // If this is a problem, consider (i.) making a fixed length
    // buffer that is much longer than any polynomials are likely to
    // be (1000?) and checking to be sure that it is not overwritten;
    // or (ii.)  writing the polynomials to a temporary file and using
    // BLAD's output counter to get the length, which would then be
    // used to allocate the proper amount of memory.
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    exhaustSummaryPolyString_m = ba0_new_printf(
      "%Az",
      exhaustSumm->tab[m] );

    // Because the function strlen() gives the number of characters in
    // the string not including the null termination, allocate enough
    // memory for the exhaustive summary polynomial output string with an
    // extra character for the null termination.
    nExhaustSummPolyChar_m = strlen( exhaustSummaryPolyString_m ) + 1;
    (*exhaustSummaryPolyOut)[m] = 
      (char*) malloc( nExhaustSummPolyChar_m * sizeof( char ) );
    
    // Set the exhaustive summary output string.
    strncpy(
      (*exhaustSummaryPolyOut)[m],
      exhaustSummaryPolyString_m,
      nExhaustSummPolyChar_m );
  }


  //----------------------------------------------------------
  // Finish up.
  //----------------------------------------------------------

  // Free the memory allocated for all of the GMP integers.
  mpz_clear( thetaRandom_r );
  mpz_clear( thetaRandom_rStartsAtZero );
  mpz_clear( thetaRandomMaxArg );
  mpz_clear( inOutRelPolyCoeffVal );
  mpz_clear( inOutRelPolyLeadingCoeffVal );

  // Undo the last call to bav_R_push_ordering().
  bav_R_pull_ordering();

  // This function restores the value of the free pointer.
  ba0_restore( &memoryMarker );

  // Call the BLAD library termination function.
  bad_terminate( ba0_init_level );

  // Return the number of exhaustive summary polynomials.
  return nExhaustSummPoly;
}

