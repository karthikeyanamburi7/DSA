import java.util.Scanner;
class Employee
{
    private final long id;
    private String name;
    private int age;
    private String qualification;
    private String position;
    private double salary;
    Employee(long id, String name, int age, String qualification, String position, double salary)
    {
        this.id=id;
        this.name=name;
        this.age=age;
        this.qualification=qualification;
        this.position=position;
        this.salary=salary;
    }
    long getId()
    {
        return id;
    }
    String getName()
    {
        return name;
    }
    int getAge()
    {
        return age;
    }
    String getQualification()
    {
        return qualification;
    }
    String getPosition()
    {
        return position;
    }
    double getSalary()
    {
        return salary;
    }
    void setName(String name)
    {
        this.name=name;
    }
    void setAge(int age)
    {
        this.age=age;
    }
    void setQualification(String qualification)
    {
        this.qualification=qualification;
    }
    void setPosition(String position)
    {
        this.position=position;
    }
    void setSalary(double salary)
    {
        this.salary=salary;
    }
    void display()
    {
        System.out.println("ID            : " + id);
        System.out.println("Name          : " + name);
        System.out.println("Age           : " + age);
        System.out.println("Qualification : " + qualification);
        System.out.println("Position      : " + position);
        System.out.println("Salary        : " + salary);
        System.out.println("-----------------------------------");
    }
}

class Node
{
    Employee employee;
    Node prev;
    Node next;
    Node(Employee employee)
    {
        this.employee=employee;
    }
}

class EmployeeHashTable
{
    private Node[] table;
    private int count;
    private long collisions;
    private int rehashCount;
    private static final double MAX_LOAD_FACTOR = 0.75;
    EmployeeHashTable(int size)
    {
        table=new Node[nextPrime(Math.max(3, size))];
    }
    private int hash(long id)
    {
        return Math.floorMod(id, table.length);
    }
    int size()
    {
        return count;
    }
    boolean insert(Employee employee)
    {
        if (employee==null)
            return false;
        if (search(employee.getId())!=null)
            return false;
        if ((double)(count + 1) / table.length > MAX_LOAD_FACTOR)
            rehash();
        int index=hash(employee.getId());
        Node newNode=new Node(employee);
        if (table[index]==null)
        {
            table[index]=newNode;
        }
        else
        {
            collisions++;
            Node current=table[index];
            while (current.next!=null)
                current=current.next;
            current.next=newNode;
            newNode.prev=current;
        }
        count++;
        return true;
    }
    Employee search(long id)
    {
        int index=hash(id);
        Node current=table[index];
        while (current!=null)
        {
            if (current.employee.getId()==id)
                return current.employee;

            current=current.next;
        }
        return null;
    }
    boolean update(long id, String name, int age, String qualification, String position,double salary)
    {
        Employee employee=search(id);
        if (employee==null)
            return false;
        employee.setName(name);
        employee.setAge(age);
        employee.setQualification(qualification);
        employee.setPosition(position);
        employee.setSalary(salary);
        return true;
    }
    boolean delete(long id)
    {
        int index=hash(id);
        Node current=table[index];
        while (current!=null)
        {
            if (current.employee.getId()==id)
            {
                if (current.prev!=null)
                    current.prev.next=current.next;
                else
                    table[index] = current.next;
                if (current.next!=null)
                    current.next.prev=current.prev;
                current.prev=null;
                current.next=null;
                count--;
                return true;
            }
            current=current.next;
        }
        return false;
    }
    void displayAll()
    {
        if (count==0)
        {
            System.out.println("No employee records found.");
            return;
        }
        for (int i=0; i<table.length; i++)
        {
            Node current=table[i];
            while (current!=null)
            {
                current.employee.display();
                current=current.next;
            }
        }
    }
    void displayStatistics()
    {
        int nonEmptyBuckets=0;
        int maxChain=0;
        for (int i=0; i<table.length; i++)
        {
            int chainLength=0;
            Node current=table[i];
            while (current!=null)
            {
                chainLength++;
                current=current.next;
            }
            if (chainLength > 0)
                nonEmptyBuckets++;
            if (chainLength > maxChain)
                maxChain=chainLength;
        }
        double loadFactor = (double) count / table.length;
        System.out.println("\n========== HASH TABLE STATISTICS ==========");
        System.out.println("Table Size       : " + table.length);
        System.out.println("Employee Count   : " + count);
        System.out.println("Load Factor      : " + loadFactor);
        System.out.println("Non-Empty Buckets: " + nonEmptyBuckets);
        System.out.println("Collisions       : " + collisions);
        System.out.println("Rehash Operations: " + rehashCount);
        System.out.println("Maximum Chain    : " + maxChain);
        System.out.println("===========================================\n");
    }
    private void rehash()
    {
        Node[] oldTable=table;
        table=new Node[nextPrime(oldTable.length * 2 + 1)];
        for (Node head : oldTable)
        {
            Node current=head;
            while (current!=null)
            {
                Node nextNode=current.next;
                current.prev=null;
                current.next=null;
                int index=hash(current.employee.getId());
                if (table[index]==null)
                {
                    table[index]=current;
                }
                else
                {
                    Node last=table[index];
                    while (last.next!=null)
                        last=last.next;

                    last.next=current;
                    current.prev=last;
                }
                current=nextNode;
            }
        }
        rehashCount++;
    }
    private boolean isPrime(int number)
    {
        if (number<2)
            return false;
        if (number==2)
            return true;
        if (number % 2==0)
            return false;
        for (int i=3;i<=number / i;i+=2)
        {
            if (number % i==0)
                return false;
        }
        return true;
    }
    private int nextPrime(int number)
    {
        while (!isPrime(number))
            number++;

        return number;
    }
}

class Input
{
    static String readNonEmpty(Scanner sc, String message)
    {
        while (true)
        {
            System.out.print(message);
            String value=sc.nextLine().trim();

            if (!value.isEmpty())
                return value;

            System.out.println("Value cannot be empty.");
        }
    }
    static long readId(Scanner sc)
    {
        while (true)
        {
            try
            {
                System.out.print("Enter Employee ID: ");
                long id=Long.parseLong(sc.nextLine().trim());
                if (id>=0)
                    return id;

                System.out.println("ID cannot be negative.");
            }
            catch (Exception e)
            {
                System.out.println("Enter a valid numeric ID.");
            }
        }
    }
    static int readAge(Scanner sc)
    {
        while (true)
        {
            try
            {
                System.out.print("Enter Age: ");
                int age=Integer.parseInt(sc.nextLine().trim());

                if (age>=18 && age<=100)
                    return age;
                System.out.println("Age must be between 18 and 100.");
            }
            catch (Exception e)
            {
                System.out.println("Enter a valid age.");
            }
        }
    }
    static double readSalary(Scanner sc)
    {
        while (true)
        {
            try
            {
                System.out.print("Enter Salary: ");
                double salary=Double.parseDouble(sc.nextLine().trim());
                if (Double.isFinite(salary) && salary >= 0)
                    return salary;

                System.out.println("Salary must be non-negative.");
            }
            catch (Exception e)
            {
                System.out.println("Enter a valid salary.");
            }
        }
    }
}

public class EmployeeRecordSystem
{
    static Scanner sc=new Scanner(System.in);
    static EmployeeHashTable hashTable=new EmployeeHashTable(5);
    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("\n========== EMPLOYEE RECORD SYSTEM ==========");
            System.out.println("1. Insert Employee");
            System.out.println("2. Search Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Display All Employees");
            System.out.println("6. Hash Table Statistics");
            System.out.println("7. Exit");
            System.out.println("============================================");
            System.out.print("Enter your choice: ");
            int choice;
            try
            {
                choice=Integer.parseInt(sc.nextLine().trim());
            }
            catch (Exception e)
            {
                System.out.println("Enter a valid choice.");
                continue;
            }
            switch (choice)
            {
                case 1:
                    insertEmployee();
                    break;
                case 2:
                    searchEmployee();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    deleteEmployee();
                    break;
                case 5:
                    hashTable.displayAll();
                    break;
                case 6:
                    hashTable.displayStatistics();
                    break;

                case 7:
                    System.out.println("Program terminated.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    static void insertEmployee()
    {
        long id=Input.readId(sc);
        if (hashTable.search(id) != null)
        {
            System.out.println("Employee ID already exists.");
            return;
        }
        String name=Input.readNonEmpty(sc, "Enter Name: ");
        int age=Input.readAge(sc);
        String qualification=Input.readNonEmpty(sc, "Enter Qualification: ");
        String position=Input.readNonEmpty(sc, "Enter Position: ");
        double salary=Input.readSalary(sc);
        Employee employee=new Employee(id, name, age, qualification, position, salary);
        if (hashTable.insert(employee))
            System.out.println("Employee inserted successfully.");
        else
            System.out.println("Employee insertion failed.");
    }
    static void searchEmployee()
    {
        long id=Input.readId(sc);

        Employee employee=hashTable.search(id);

        if (employee !=null)
        {
            System.out.println("\nEmployee Found:");
            employee.display();
        }
        else
        {
            System.out.println("Employee not found.");
        }
    }
    static void updateEmployee()
    {
        long id=Input.readId(sc);
        if (hashTable.search(id)==null)
        {
            System.out.println("Employee not found.");
            return;
        }
        String name=Input.readNonEmpty(sc, "Enter New Name: ");
        int age=Input.readAge(sc);
        String qualification=Input.readNonEmpty(sc, "Enter New Qualification: ");
        String position=Input.readNonEmpty(sc, "Enter New Position: ");

        double salary=Input.readSalary(sc);
        if (hashTable.update( id, name, age, qualification, position, salary))
        {
            System.out.println("Employee updated successfully.");
        }
        else
        {
            System.out.println("Update failed.");
        }
    }
    static void deleteEmployee()
    {
        long id=Input.readId(sc);
        if (hashTable.delete(id))
            System.out.println("Employee deleted successfully.");
        else
            System.out.println("Employee not found.");
    }
}