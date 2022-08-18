package main;
/* Complete all the methods.
EBNF of Mini Language
Program" --> "("Sequence State")".
Sequence --> "("Statements")".
Statements --> Stmt*
Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
State -->  "("Pairs")".
Pairs -->  Pair*.
Pair --> "("Identifier Literal")".
NullStatement --> "skip".
Assignment --> "assign" Identifier Expression.
Conditional --> "conditional" Expression Stmt Stmt.
Loop --> "loop" Expression Stmt.
Block --> "block" Statements.
Expression --> Identifier | Literal | "("Operation Expression Expression")".
Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

Note: Treat Identifier and Literal as terminal symbols. Every symbol inside " and " is a terminal symbol. The rest are non terminals.

*/
public class Parser{
  private Token currentToken;
  Scanner scanner;

  private void accept(byte expectedKind) {
    if (currentToken.kind == expectedKind)
      currentToken = scanner.scan();
    else
      new Error("Syntax error: " + currentToken.spelling + " is not expected.",
                currentToken.line);
  }

  private void acceptIt() {
    currentToken = scanner.scan();
  }

  public void parse() {
    SourceFile sourceFile = new SourceFile();
    scanner = new Scanner(sourceFile.openFile());
    currentToken = scanner.scan();
    parseProgram();
    if (currentToken.kind != Token.EOT)
      new Error("Syntax error: Redundant characters at the end of program.",
                currentToken.line);
  }

  //Program" --> "("Sequence State")".
  private void parseProgram() {
	  accept((byte)9);
	  parseSequence();
	  parseState();
	  accept((byte)10);
  }

  //Sequence --> "("Statements")".
  private void parseSequence(){
	  accept((byte)9);
	  parseStatements();
	  accept((byte)10);
  }

  //Statements --> Stmt*
  private void parseStatements(){
	  while(currentToken.kind != 12)
	  {
		  parseStmt();
	  }
  }

  //Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
  private void parseStmt(){
	  accept((byte)9); //LPAREN
	  
	  	  if(currentToken.kind == 6)
	  	  {
	  		  parseNullStatement();
	  	  }	  
		  if(currentToken.kind == 2)
		  {
			  parseAssignment();
		  }
		  else if(currentToken.kind == 3)
		  {
			  parseConditional();
		  }
		  else if(currentToken.kind == 4)
		  {
			  parseLoop();
		  }
		  else if(currentToken.kind == 5)
		  {
			  parseBlock();
		  }
	  
	  accept((byte)10); //RPAREN
	  
  }

  //State -->  "("Pairs")".
  private void parseState(){
	  accept((byte)9);
	  parsePairs();
	  accept((byte)10);
  }

  //Pairs --> Pair*.
  private void parsePairs(){
	  while(currentToken.kind != 10)
	  {
		  parsePair();
	  }
  }

  //Pair --> "("Identifier Literal")".
  private void parsePair(){
	  //Check Later
	  accept((byte)9);
	  accept((byte)0);
	  accept((byte)1);
	  accept((byte)10);
  }

  //NullStatement --> skip.
  private void parseNullStatement(){
	  accept((byte)6);
  }

  //Assignment --> "assign" Identifier Expression.
  private void parseAssignment(){
	  accept((byte)2);
	  accept((byte)0);
	  parseExpression();
  }

  //Conditional --> "conditional" Expression Stmt Stmt.
  private void parseConditional(){
	  accept((byte)3);
	  parseExpression();
	  parseStmt();
	  parseStmt();
  }

  //Loop --> "loop" Expression Stmt.
  private void parseLoop(){
	  accept((byte)4);
	  parseExpression();
	  parseStmt();
  }

  //Block --> "block" Statements.
  private void parseBlock(){
	  accept((byte)5);
	  parseStatements();
  }

  //Expression --> Identifier | Literal | "("Operation Expression Expression")".
  private void parseExpression()
  {
	  if(currentToken.kind == 0 || currentToken.kind == 1)
	  {
		  acceptIt();
	  }
	  else
	  {
		  accept((byte)9);
		  parseOperation();
		  parseExpression();
		  parseExpression();
		  accept((byte)10);
	  }
  }

  //Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".
  private void parseOperation()
  {
	  if(currentToken.kind == 11)
	  {
		  accept((byte)11);
	  }
	  else if(currentToken.kind == 8)
	  {
		  accept((byte)8);
	  }
	  else if(currentToken.kind == 9)
	  {
		  accept((byte)9);
	  }
	  
  }
}
