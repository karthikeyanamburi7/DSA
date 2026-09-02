#include<stdio.h>
int main(){
    int n,i;
    int a[50];
    int key;
    int low,high,mid;
    int found=0;
    int c=0;
    printf("enter the number of employee");
    scanf("%d",&n);
    printf("enter %d employee id in ascending order:\n",n);
    for(i=0;i<n;i++){
        scanf("%d",&a[i]);
    }
    printf("enter the employee id to search:");
    scanf("%d",&key);
    low=0;
    high=n-1;
    while(low<=high){
        c++;
        mid=(low+high)/2;
        if(a[mid]==key){
            found=1;
            break;
        }
        else if(key<a[mid]){
            high=mid-1;
        }
        else{
            low=mid+1;
        }
    }
    if(found){
        printf("employee id found at index %d\n",mid+1);
    }
    else{
        printf("employee id not found.\n");
    }
    printf("number of comparisons=%d\n",c);
    return 0;
}
