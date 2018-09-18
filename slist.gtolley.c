#include "slist.gtolley.h"
/*
* George Tolley
*
* Singly-Linked List Implementation, with functions to print,
* insert at the end of the list, insert as a sorted element,
* and delete an element from the list.
*
*/

void printList (SListNode *theList); // print the list items from head to tail
int insertAtEnd(SListNode **theList, int data); // return zero if successful
int insertSorted(SListNode **theList, int data); // return zero if successful
int isInList(SListNode *theList, int data);
// return non-zero value if the value is in the list, otherwise return 0.
int deleteFromList(SListNode **theList, int data);
// if the value isn't in the list, do nothing and return a non-zero value.
// Otherwise, delete all instances of the number and return 0.
