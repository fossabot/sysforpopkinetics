#ifndef EXPTREEGENERATOR_H
#define EXPTREEGENERATOR_H

#include <vector>
#include <xercesc/dom/DOM.hpp>
#include "ExpNodeCarrier.h"
#include "SpkCompilerUtil.h"
#include "SymbolTable.h"
#include "ExpTreeGenerator.h"
#include "../libnonmem/explang.tab.h"
/**
 * @file ExpTreeGenerator.h
 *
 * Declares ExpTreeGenerator class
 */
/**
 * ExpTreeGenerator class encapsulates the resource allocation/deallocation
 * mechanisum that supports providing the caller with the interface to 
 * a client-specific yyparse(). 
 *
 * A yyparse() is a C++ function generated by YACC (or YACC compatable).
 * In the context of Spk Compiler, a yyparse() is a parser
 * that reads a set of mathemtical expressions written
 * in a client-specific language (ex. NONMEM abbriviated code)
 * and generates a expression tree in the form that is client
 * independent.  The expression tree is implemented by using
 * DOMDocument; In other words, the tree is an xml document object.
 * Further, yyparse() requires/calls a lexical analizer named
 * yylex().  A yylex() is a C function that reads in the input
 * stream a character by character and returns a token when it 
 * recognizes a legal word to the caller which is yyparse().
 *
 * Our yylex() expects the following variables to be globally
 * accesible (remember, yylex() is a C function):
 *
 * - yyin
 * - yydebug
 * - line counter
 * - error counter
 *
 * Our yyparse() initializes the following variables
 * with arbitary values prior to the first call to yylex():
 *
 * - yyin
 * - yydebug
 * - line counter
 * - error counter
 * - symbol table
 *
 * The ExpTreeGenerator::parse(), the interface to yypase(),
 * allows the user to pass in these initial values to the above
 * variables so that the method can be called many times
 * arbitrarily.
 *
 * Furthermore, our yyparse() uses methods to creates
 * NodeCarrier objects as it builds an expression tree.
 * The mechanism to keep track of resources allocated for
 * these NodeCarrier objects is provided internally within
 * ExpTreeGenerator class.
 *
 */
class ExpTreeGenerator {

 public:
  /**
   * The default constructor.  
   * 
   * This constructor initializes exercesc::DOMPlatformUtils 
   * with locale=en_US.
   * Initialization of DOMPlatformUtil is required prior to 
   * the first use of xercesc DOM related features.
   * DOM features are used in building expression trees which
   * are implemented in terms of DOMDocument (as an xml object).
   *
   * Note that when initialized through this constructor, 
   * DOMPlatformUtils shall be terminated in the
   * ExpTreeGenerator destructor.  The termination is effective
   * per process.  So, use the other constructor that allows
   * you to tell the class object not to 
   * call DOMPlatformUtils::Terminate() in the destructor.
   */
  ExpTreeGenerator();

  /**
   * A constructor that allows the user to set a flag that indicates
   * as to whether to call DOMPlatformUtils::Terminate() or not.
   * 
   * If shouldTerminate is given true, exercesc::DOMPlatformUtils 
   * shall be initialied with locale=en_US and will be
   * terminated when the object goes out of scope.
   *
   * Initialization of DOMPlatformUtil is required prior to 
   * the first use of xercesc DOM related features.
   * DOM features are used in building expression trees which
   * are implemented in terms of DOMDocument (as an xml object).
   *
   */
  ExpTreeGenerator( bool shouldTerminate );

  /**
   * The destructor. 
   *
   * The destructor releases resources allocated for objects
   * created/used during the object's life cycle.  
   * It also terminates the DOMPlatformUtil settings which are
   * effective per process, unless the user told not to do so
   * or created this object though the default constructor.
   *
   */
  ~ExpTreeGenerator();

  /**
   * @return a pointer to a valid DOMDocument object.
   * The pointer to the object will be used to directly create/manipulate
   *  DOM elements by the user.
   */
  xercesc::DOMDocument * getRoot( void ) const;

  /**
   * Create an empty ExpNodeCarrier data structure object.  
   *
   * The created object is 
   * empty in a sense that its @c node element is pointing to nothing.  The user
   * is responsible for creating a DOMElement object 
   * (using @c ExpTreeGenerator::getRoot()->createElement(const XMLCh*))
   * and set the @c node pointer to poin to it.
   *
   * @return a pointer to a ExpNodeCarrier data structure object.
   *
   */
  struct ExpNodeCarrier * createExpNodeCarrier( void );

  /**
   * Releases resources allocated for ExpNodeCarrier objects.
   *
   * This method immediately releases the resources allocated for those 
   * ExpNodeCarrier objects which were created
   * via ExpTreeGenerator::createExpNodeCarrier().
   *
   * @return the number of ExpNodeCarrier objects whose allocated resources
   * have been released.
   */
  int releaseExpNodeCarriers( void );

  /**
   * Prints out the DOM-based tree to the standard output.
   */
  void printToStdout( void ) const;

  /**
   * Prints out the DOM-based tree to a file.
   *
   * @param filename is a path to a file into which tree contents are written.
   */
  void printToFile( const char* filename ) const;


 private:
  xercesc::DOMImplementation * impl;
  xercesc::DOMDocument * doc;
  xercesc::DOMElement * root;

  bool isToTerminate;

  std::vector<struct ExpNodeCarrier*> nodes;


  /**
   * 
   */
  DOMDocument* createTree( const XMLCh* name );

};


#endif
