#include <iostream>
#include <valarray>
#include <fstream>
#include <string>

#include "explangTest.h"

#include <cppunit/TestFixture.h>
#include <cppunit/TestCaller.h>
#include <cppunit/TestSuite.h>
#include <cppunit/TextTestResult.h>
#include <cppunit/ui/text/TestRunner.h>

#include <xercesc/util/XMLString.hpp>
#include <xercesc/util/PlatformUtils.hpp>

#include <spkcompiler/SymbolTable.h>
#include <spkcompiler/ExpTreeGenerator.h>
#include <spkcompiler/nonmem/explang.h>

using namespace std;
using namespace CppUnit;
using namespace xercesc;

extern "C"{
  int yylex(void);  
  int yyparse(void);
};
extern int                gSpkExpLines;
extern int                gSpkExpErrors;
extern SymbolTable      * gSpkExpSymbolTable;
extern FILE             * gSpkExpOutput;
extern FILE             * yyin;
extern int                yydebug;


void explangTest::setUp()
{
}
void explangTest::tearDown()
{
}
void explangTest::testScalarAssignmentToScalar()
{
  // Attention!!! yylex() converts any capitalized letter to lower case.

  SymbolTable table;
  char input[]        = "testScalarAssignmentToScalar.in";
  char output[]       = "testScalarAssignmentToScalar.out";

  FILE * pInput = fopen( input, "w" );
  CPPUNIT_ASSERT( pInput != NULL );
  fputs( "A = 1\n",       pInput );
  fputs( "A = 1.0\n",     pInput );
  fputs( "A = 1.E01\n",   pInput );
  fputs( "A = 1.0E01\n",  pInput );
  fputs( "A = 1.0E1\n",   pInput );
  fputs( "A = +1\n",      pInput );
  fputs( "A = +1.0\n",    pInput );
  fputs( "A = -1.E01\n",  pInput );
  fputs( "A = -1.0E01\n", pInput );
  fputs( "A = -1.0E1\n",  pInput );
  fputs( "A = (1)\n",     pInput );
  fputs( "A = +(1)\n",    pInput );
  fputs( "A = -(1)\n",    pInput );

  fclose( pInput );

  pInput = fopen( input, "r" );
  CPPUNIT_ASSERT( pInput != NULL );

  yyin = pInput;
  CPPUNIT_ASSERT( yyin != NULL );

  gSpkExpErrors = 0;
  gSpkExpLines  = 0;
  gSpkExpSymbolTable = &table;
  CPPUNIT_ASSERT( gSpkExpSymbolTable != NULL );
  gSpkExpOutput = fopen( output, "w" );
  yydebug = 0;

  yyparse();

  CPPUNIT_ASSERT( table.find( "a" ));
  fclose( pInput );
  fclose( gSpkExpOutput );

  CPPUNIT_ASSERT( gSpkExpErrors==0 );
  if( gSpkExpErrors == 0 )
  {
    //expTreeUtil.printToStdout( gSpkExpTree );
  }

  //cout << endl;
  //gSpkExpSymbolTable->dump();

  string buf;
  ifstream pOutput( output );
  CPPUNIT_ASSERT( pOutput.good() );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1.0;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1.e01;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1.0e01;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1.0e1;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "+1;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "+1.0;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-1.e01;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-1.0e01;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-1.0e1;" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "+(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );
  
  pOutput >> buf;  
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput.close();

  remove( input );
  remove( output );
}
void explangTest::testVectorElementAssignmentToScalar()
{
  SymbolTable table;
  
  char input[]        = "testVectorElementAssignmentToScalar.in";
  char output[]       = "testVectorElementAssignmentToScalar.out";
  char statementIn[]  = "A(1) = 1\n";

  FILE * pInput = fopen( input, "w" );
  CPPUNIT_ASSERT( pInput != NULL );
  fputs( statementIn, pInput );
  fclose( pInput );

  pInput = fopen( input, "r" );
  CPPUNIT_ASSERT( pInput != NULL );

  yyin = pInput;
  CPPUNIT_ASSERT( yyin != NULL );

  gSpkExpErrors = 0;
  gSpkExpLines  = 0;
  gSpkExpSymbolTable = &table;
  CPPUNIT_ASSERT( gSpkExpSymbolTable != NULL );
  gSpkExpOutput = fopen( output, "w" );
  yydebug = 0;

  yyparse();

  CPPUNIT_ASSERT( table.find( "a" ));
  
  fclose( pInput );
  fclose( gSpkExpOutput );

  CPPUNIT_ASSERT( gSpkExpErrors==0 );
  if( gSpkExpErrors == 0 )
  {
    //expTreeUtil.printToStdout( gSpkExpTree );
  }

  //cout << endl;
  //gSpkExpSymbolTable->dump();

  string buf;
  ifstream pOutput( output );
  CPPUNIT_ASSERT( pOutput.good() );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "]" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1;" );

  pOutput.close();
  remove( input );
  remove( output );
}
void explangTest::testFunctions()
{
  SymbolTable table;
  char input[]        = "testFunctions.in";
  char output[]       = "testFunctions.out";

  FILE * pInput = fopen( input, "w" );
  CPPUNIT_ASSERT( pInput != NULL );
  fputs( "A = EXP(X)\n", pInput );
  fputs( "B(1) = EXP(X)\n", pInput );
  fputs( "A = LOG(1)\n", pInput );
  fputs( "A = LOG10(100)\n", pInput );
  fputs( "A = SQRT(4.0)\n", pInput );
  fputs( "A = B( X**Y )\n", pInput );
  fputs( "A = EXP(X) * SQRT(Y)\n", pInput );
  fputs( "A = 1.0 * ( EXP(X) + Y )\n", pInput ); 
  
  fclose( pInput );

  Symbol XX( "X", Symbol::SCALAR, Symbol::DOUBLE, true );
  Symbol YY( "Y", Symbol::SCALAR, Symbol::DOUBLE, true );
  table.insert( XX );
  table.insert( YY );
  
  pInput = fopen( input, "r" );
  CPPUNIT_ASSERT( pInput != NULL );

  yyin = pInput;
  CPPUNIT_ASSERT( yyin != NULL );

  gSpkExpErrors = 0;
  gSpkExpLines  = 0;
  gSpkExpSymbolTable = &table;
  CPPUNIT_ASSERT( gSpkExpSymbolTable != NULL );
  gSpkExpOutput = fopen( output, "w" );
  yydebug = 0;

  yyparse();

  CPPUNIT_ASSERT( table.find( "a" ));
  CPPUNIT_ASSERT( table.find( "b" ));
  
  fclose( pInput );
  fclose( gSpkExpOutput );

  CPPUNIT_ASSERT( gSpkExpErrors==0 );
  if( gSpkExpErrors == 0 )
  {
    //expTreeUtil.printToStdout( gSpkExpTree );
  }

  //cout << endl;
  //gSpkExpSymbolTable->dump();

  string buf;
  ifstream pOutput( output );
  CPPUNIT_ASSERT( pOutput.good() );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "exp(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "b[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "]" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "exp(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "log(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "log10(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "100" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "sqrt(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "4.0" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "b[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "pow(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x," );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "];" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "exp(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "*" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "sqrt(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1.0" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "*" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "exp(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "+" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ");" );

  pOutput.close();
  remove( input );
  remove( output );
}

void explangTest::testIfStmt()
{
  SymbolTable table;
  
  char input[]        = "testIfStmt.in";
  char output[]       = "testIfStmt.out";

  FILE * pInput = fopen( input, "w" );
  CPPUNIT_ASSERT( pInput != NULL );
  fputs( "IF( X.NE.Y ) A = B(1)\n", pInput );
  fputs( "IF( X.NE.Y ) A = B(1) ;comment\n", pInput );
  fclose( pInput );

  Symbol XX( "X", Symbol::SCALAR, Symbol::DOUBLE, true );
  Symbol YY( "Y", Symbol::SCALAR, Symbol::DOUBLE, true );
  Symbol BB( "B", Symbol::VECTOR, Symbol::DOUBLE, true );
  table.insert( XX );
  table.insert( YY );
  table.insert( BB );
  
  pInput = fopen( input, "r" );
  CPPUNIT_ASSERT( pInput != NULL );

  yyin = pInput;
  CPPUNIT_ASSERT( yyin != NULL );

  gSpkExpErrors = 0;
  gSpkExpLines  = 0;
  gSpkExpSymbolTable = &table;
  CPPUNIT_ASSERT( gSpkExpSymbolTable != NULL );
  gSpkExpOutput = fopen( output, "w" );
  yydebug = 0;

  yyparse();

  CPPUNIT_ASSERT( table.find( "a" ) );
  
  fclose( pInput );
  fclose( gSpkExpOutput );

  CPPUNIT_ASSERT( gSpkExpErrors==0 );
  if( gSpkExpErrors == 0 )
  {
    //expTreeUtil.printToStdout( gSpkExpTree );
  }

  //cout << endl;
  //gSpkExpSymbolTable->dump();

  string buf;
  ifstream pOutput( output );
  CPPUNIT_ASSERT( pOutput.good() );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "if(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "!=" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "{" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "b[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "];" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "}" );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "if(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "!=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "{" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "b[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "];" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "}" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "//" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "comment" );

  pOutput.close();
  remove( input );
  remove( output );
}
void explangTest::testIfThenStmt()
{
  SymbolTable table;
  
  char input[]        = "testIfThenStmt.in";
  char output[]       = "testIfTenStmt.out";

  FILE * pInput = fopen( input, "w" );
  CPPUNIT_ASSERT( pInput != NULL );
  fputs( "IF( X.NE.Y ) THEN\n", pInput );
  fputs( "A = B(1)\n", pInput );
  fputs( "C = D   ;comment\n", pInput );
  fputs( "ENDIF\n", pInput );
  fclose( pInput );

  Symbol XX( "X", Symbol::SCALAR, Symbol::DOUBLE, true );
  Symbol YY( "Y", Symbol::SCALAR, Symbol::DOUBLE, true );
  Symbol BB( "B", Symbol::VECTOR, Symbol::DOUBLE, true );
  Symbol DD( "D", Symbol::SCALAR, Symbol::DOUBLE, true );

  table.insert( XX );
  table.insert( YY );
  table.insert( BB );
  table.insert( DD );

  pInput = fopen( input, "r" );
  CPPUNIT_ASSERT( pInput != NULL );

  yyin = pInput;
  CPPUNIT_ASSERT( yyin != NULL );

  gSpkExpErrors = 0;
  gSpkExpLines  = 0;
  gSpkExpSymbolTable = &table;
  CPPUNIT_ASSERT( gSpkExpSymbolTable != NULL );
  gSpkExpOutput = fopen( output, "w" );
  yydebug = 0;

  yyparse();

  CPPUNIT_ASSERT( table.find( "c" ) );
  CPPUNIT_ASSERT( table.find( "a" ) );
  fclose( pInput );
  fclose( gSpkExpOutput );

  CPPUNIT_ASSERT( gSpkExpErrors==0 );
  if( gSpkExpErrors == 0 )
  {
    //expTreeUtil.printToStdout( gSpkExpTree );
  }

  //cout << endl;
  //gSpkExpSymbolTable->dump();

  string buf;
  ifstream pOutput( output );
  CPPUNIT_ASSERT( pOutput.good() );

  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "if(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "x" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "!=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "y" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "{" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "a" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "b[" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "(" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == ")" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "-" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "1" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "];" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "c" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "=" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "d;" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "//" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "comment" );
  pOutput >> buf;
  CPPUNIT_ASSERT_MESSAGE( buf, buf == "}" );

  pOutput.close();
  remove( input );
  remove( output );
}

CppUnit::Test * explangTest::suite()
{
  CppUnit::TestSuite *suiteOfTests = new CppUnit::TestSuite( "explangTest" );

  suiteOfTests->addTest( 
     new CppUnit::TestCaller<explangTest>(
         "testScalarAssignmentToScalar", 
	 &explangTest::testScalarAssignmentToScalar ) );

  suiteOfTests->addTest( 
     new CppUnit::TestCaller<explangTest>(
         "testVectorElementAssignmentToScalar", 
	 &explangTest::testVectorElementAssignmentToScalar ) );

  suiteOfTests->addTest( 
     new CppUnit::TestCaller<explangTest>(
         "testFunctions",
	 &explangTest::testFunctions ) );

  suiteOfTests->addTest( 
     new CppUnit::TestCaller<explangTest>(
         "testIfStmt",
	 &explangTest::testIfStmt) );

  suiteOfTests->addTest( 
     new CppUnit::TestCaller<explangTest>(
         "testIfThenStmt",
	 &explangTest::testIfThenStmt) );
  
     return suiteOfTests;
}

