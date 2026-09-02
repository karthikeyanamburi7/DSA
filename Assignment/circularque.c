#include <stdio.h>
#define MAX_SIZE 5
int queue[MAX_SIZE];
int front=-1;
int rear=-1;
int isFull(){
    return ((rear+1)%MAX_SIZE==front);
}
int isEmpty() {
    return (front==-1);
}
void enqueue(int value)
{
    if (isFull())
        {
        printf("Queue Overflow\n");
        return;
        }
    if (isEmpty())
        {
        front=0;
        }
    rear=(rear+1)%MAX_SIZE;
    queue[rear]=value;
    printf("Enqueued: %d\n",value);
}

int dequeue() {
    if (isEmpty()) {
        printf("Queue Underflow\n");
        return -1;
    }
    int value=queue[front];
    if (front==rear)
        {
        front=rear=-1;
    } else {
        front=(front+1)%MAX_SIZE;
    }
    printf("Dequeued: %d\n",value);
    return value;
}

int peek(){
    if (isEmpty()){
        printf("Queue is empty\n");
        return -1;
    }
    return queue[front];
}
void displayQueue(){
    if (isEmpty()){
        printf("Queue is empty\n");
        return;
    }
    printf("Queue contents: ");
    int i=front;
    while (1){
        printf("%d ",queue[i]);
        if (i==rear) break;
        i=(i+1)%MAX_SIZE;
    }
    printf("\n");
}
int main() {
    int choice,value;
    while(1){
        printf("\n---circular queue---\n");
        printf("1.enqueue\n");
        printf("2.dequeue\n");
        printf("3.peek\n");
        printf("4.display\n");
        printf("5.exit\n");
        printf("enter the choice:");
        scanf("%d",&choice);
        switch(choice){
            case 1:
                printf("enter value: ");
                scanf("%d",&value);
                enqueue(value);
                break;
            case 2:
                dequeue();
                break;
            case 3:
                printf("front element: %d\n",peek());
                break;
            case 4:
                displayQueue();
                break;
            case 5:
                printf("program ended.\n");            
                return 0;
            default:
                printf("invalid choice!\n");
        }
    }
}