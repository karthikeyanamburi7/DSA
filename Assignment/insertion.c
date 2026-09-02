#include<stdio.h>
int main(){
    int a[50],n;
    int i,j,key;
    int s=0;
    printf("enter number of marks: ");
    scanf("%d",&n);
    printf("enter the marks: \n");
    for(i=0;i<n;i++){
        scanf("%d",&a[i]);
    }
    for(i=0;i<n;i++){
        key=a[i];
        j=i-1;
        while(j>=0&&a[j]>key){
            a[j+1]=a[j];
            j--;
            s++;
        }
        a[j+1]=key;
        printf("after pass %d: ",i);
        for(int k=0;k<n;k++){
            printf("%d  ",a[k]);
        }
        printf("\n");
    }
    printf("\n final sorted marks: ");
    for(i=0;i<n;i++){
        printf("%d  ",a[i]);
    }
    printf("\n total shifts =%d\n",s);
    return 0;
}