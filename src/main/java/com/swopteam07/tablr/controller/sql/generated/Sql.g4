grammar Sql;

options{
language = Java;
}

WS : (' ' | '\t' | '\r\n' | '\n' )+ -> channel(HIDDEN); //whitespace options


query
 : select=select_stmnt
   from=from_stmnt
   where=where_stmnt?
   EOF
;

select_stmnt
 : K_SELECT result_column ( ',' result_column )*
 ;

from_stmnt
 : K_FROM ( table | join_clause )
 ;

where_stmnt
 : K_WHERE value_expr
 ;

result_column
 : '*'
 | table_name '.' '*'
 | table_name '.' column_name ( alias )?
 | table_name alias
 ;

table
 : ( database_name '.' )? table_name ( alias )?
 ;


join_constraint
 : ( K_ON cell_name '=' cell_name
   | K_USING '(' column_name ( ',' column_name )* ')' )?
 ;

join_operator
 : K_INNER K_JOIN
 ;

join_clause
 : table join_operator table join_constraint
 | join_clause join_operator table join_constraint
 ;

//expr
// : left=expr op=binary_operator right=expr
// | '(' expr ')'
// | value_expr
// | cell_name
// ;

value_expr
 : left=value_expr op=expr_operator ('(' right=value_expr ')' | right=value_expr )
 | literal_value
 | boolean_value
 | cell_name
;

literal_value
 : NUMERIC_LITERAL
 | '"' any_name '"'
 ;

boolean_value
 : K_TRUE
 | K_FALSE
 ;

alias
 : K_AS any_name
 ;

expr_operator
 : K_OR
 | K_AND
 | K_EQUAL
 | K_ST
 | K_LT
 | K_PLUS
 | K_MINUS
 ;

K_EQUAL : '=';
K_ST: '<';
K_LT: '>';
K_PLUS : '+';
K_MINUS: '-';

K_SELECT: S E L E C T;
K_FROM: F R O M;
K_WHERE: W H E R E;
K_AS: A S;
K_INNER: I N N E R;
K_JOIN: J O I N;
K_ON: O N;
K_USING: U S I N G;
K_TRUE: T R U E;
K_FALSE: F A L S E;
K_OR: O R;
K_AND: A N D;

fragment NUMMER : [0-9];
fragment A : [aA]; // match either an 'a' or 'A'
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];



cell_name
 : ( ( database_name '.' )? table_name '.' )? column_name
 ;

column_name
 : any_name
 ;

table_name
 : any_name
 ;

 database_name
  : any_name
  ;

any_name
 : STRING_LITERAL
 | '(' any_name ')'
 ;

NUMERIC_LITERAL
 : ( '+' | '-')? ( NUMMER NUMMER*  | NUMMER? '.' NUMMER NUMMER* )
 ;

STRING_LITERAL
 : ([a-zA-Z]|'_'|'-')+
 ;