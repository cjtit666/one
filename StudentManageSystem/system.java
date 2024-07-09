package StudentManageSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class system {

    public static void main(String[] args) {
        //定义用户集合
        ArrayList<User> users = new ArrayList<>();
        User u1 = new User("tt098", "123456", "350525200409130013", "18359861828");
        //将用户添加入系统
        users.add(u1);


        //定义学生集合
        ArrayList<Student> students = new ArrayList<>();
        Student student1=new Student("tt001","张三",17,"上海");
        Student student2=new Student("tt002","李四",18,"北京");
        Student student3=new Student("tt003","王五",19,"深圳");
        //将三个学生添加入系统
        students.add(student1);
        students.add(student2);
        students.add(student3);

        //登陆界面
        enter(users,students);

        //执行系统
        //menu(students);


    }

    //定义初始菜单
    public static void menu(ArrayList<Student> students) {
        System.out.println("\"---------------欢迎来到学生管理系统---------------\"");
        System.out.println("\"1:添加学生\"");
        System.out.println("\"2:删除学生\"");
        System.out.println("\"3:修改学生\"");
        System.out.println("\"4:查询学生\"");
        System.out.println("\"5:退出\"");
        System.out.println("\"请输入您的选择:\"");

        //输入数字选择
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                addStudent(students);
                break;
            case 2:
                deleteStudent(students);
                break;
            case 3:
                setStudent(students);
                break;
            case 4:
                selectStudent(students);
                break;
            case 5:
                break;//直接退出
            default:
                System.out.println("无效操作");
                menu(students);
        }

    }

    //登陆界面
    public static void enter(ArrayList<User> users,ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\"欢迎来到学生管理系统\"");
        System.out.println("\"请选择操作 1登录 2注册 3忘记密码\"");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                signin(users,students);
                break;
            case 2:
                signup(users,students);
                break;
            case 3:
                forgot(users,students);
                break;
            default:
                System.out.println("操作无效!");
                break;
        }
    }

    //登录操作
    public static void signin(ArrayList<User> users,ArrayList<Student> students) {
        //键盘录入用户名
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String name = sc.next();
        //检查用户名
        if (!containsUser(users, name)) {
            System.out.println("用户未注册，请先注册！");
            enter(users,students);
        }
        //键盘录入密码,有三次机会
        System.out.println("请输入密码:");
        String pass = sc.next();
        //随机生成一个验证码
        String yzm = makeYZM();
        //键盘录入验证码
        System.out.println("请输入验证码:" + "(" + yzm + ")");
        String yanzhengma = sc.next();

        //判断验证码是否输入正确
        while (!samePassword(yanzhengma, yzm)) {
            System.out.println("验证码错误，请重新输入");
            yzm = makeYZM();
            System.out.println("请输入验证码:" + "(" + yzm + ")");
            yanzhengma = sc.next();
        }

        //根据用户名匹配索引
        int index = indexForUser(users, name);

        //验证码正确后，检查密码
        for (int i = 0; i < 3; i++) {
            if (samePassword(pass, users.get(index).getKey())) {
                System.out.println("登陆成功！");
                break;
            } else {
                if (i < 2) {
                    System.out.println("密码错误，您还有" + (2 - i) + "次机会");
                    System.out.println("请输入密码:");
                    pass = sc.next();
                }
                if (i == 2) {
                    System.out.println("密码错误，您还有" + (2 - i) + "次机会");
                    enter(users,students);
                }
            }
        }
        //登录成功，进入主菜单
        menu(students);
    }

    //方法：根据用户名匹配索引
    public static int indexForUser(ArrayList<User> users, String name) {
        int index = -1;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name)) {
                index = i;
            }
        }
        return index;
    }

    //方法：随机生成验证码
    public static String makeYZM() {//验证码五位数，4个大小写字母1个数字，位置随机
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        StringBuilder YZM = new StringBuilder();
        char[] zifu = new char[62];
        for (int i = 0; i < 26; i++) {
            zifu[i] = (char) ('a' + i);
        }
        for (int i = 26; i < 52; i++) {
            zifu[i] = (char) ('A' + i - 26);
        }
        for (int i = 52; i < 62; i++) {
            zifu[i] = (char) ('0' + i - 52);
        }
        for (int i = 0; i < 4; i++) {
            int index1 = r.nextInt(52);
            sb.append(zifu[index1]);
        }
        int index2 = r.nextInt(10) + 52;
        sb.append(zifu[index2]);
        char[] yzm = sb.toString().toCharArray();
        for (int i = 0; i < yzm.length; i++) {
            char temp = yzm[i];
            int index3 = r.nextInt(yzm.length);
            yzm[i] = yzm[index3];
            yzm[index3] = temp;
        }
        for (int i = 0; i < yzm.length; i++) {
            YZM.append(yzm[i]);
        }
        String yanzhengma = YZM.toString();
        return yanzhengma;
    }

    //注册
    public static ArrayList<User> signup(ArrayList<User> users,ArrayList<Student> students) {
        User newuser = new User();
        Scanner sc = new Scanner(System.in);
        //注册用户名
        System.out.println("请输入用户名(长度在3-15位之间，只能是字母加数字的组合，但不能是纯数字):");
        String name = sc.next();
        //判断用户名是否正确
        while (!rightUserName(name)) {
            System.out.println("用户名格式不正确，请重新输入!");
            System.out.println("请输入用户名(长度在3-15位之间，只能是字母加数字的组合，但不能是纯数字):");
            name = sc.next();
        }

        //判断用户名是否重复
        while (containsUser(users, name)) {
            System.out.println("该用户名已存在，请更改！");
            System.out.println("请输入用户名:");
            name = sc.next();
        }
        newuser.setName(name);

        //注册密码
        System.out.println("请设置密码");
        String password1 = sc.next();
        System.out.println("请再次输入确认密码");
        String password2 = sc.next();
        //判断两个密码是否一致
        while (!samePassword(password1, password2)) {
            System.out.println("两次密码输入不一致，请重新设置输入密码！");
            password1 = sc.next();
            System.out.println("请再次输入确认密码");
            password2 = sc.next();
        }
        newuser.setKey(password2);

        //注册身份证验证
        System.out.println("请输入身份证号:");
        String idnumber = sc.next();
        //验证身份证号正确性
        while (!rightIdNumber(idnumber)) {
            System.out.println("身份证号不正确，请重新输入身份证号:");
            idnumber = sc.next();
        }
        newuser.setIdnum(idnumber);

        //注册手机号验证
        System.out.println("请输入手机号码:");
        String tel = sc.next();
        //验证手机号的正确性
        while (!rightTel(tel)) {
            System.out.println("手机号码不正确，请重新输入手机号码:");
            tel = sc.next();
        }
        newuser.setTel(tel);

        //添加新建的用户到集合中
        users.add(newuser);
        System.out.println("用户注册成功！");
        enter(users,students);
        return users;
    }

    //方法：检查用户名是否重复
    public static boolean containsUser(ArrayList<User> users, String username) {
        boolean flag = false;
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getName())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //方法：检查用户名格式是否正确
    public static boolean rightUserName(String name) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = true;
        char[] username = name.toCharArray();
        int length = name.length();
        if (length >= 3 && length <= 15) {
            for (int i = 0; i < username.length; i++) {
                if ((username[i] >= 'a' && username[i] <= 'z') || (username[i] >= 'A' && username[i] <= 'Z')) {
                    flag1 = true;
                    break;
                }
            }
            for (int i = 0; i < username.length; i++) {
                if (!((username[i] >= 'a' && username[i] <= 'z') || (username[i] >= 'A' && username[i] <= 'Z') ||
                        (username[i] >= '0' && username[i] <= '9'))) {
                    flag2 = false;
                    break;
                }
            }
        } else {
            flag = false;
        }
        flag = flag1 && flag2;
        return flag;
    }

    //方法：判断两次输入密码是否一致
    public static boolean samePassword(String s1, String s2) {
        boolean flag = false;
        if (s1.equals(s2)) {
            flag = true;
        }
        return flag;
    }

    //方法：验证身份证号正确性
    public static boolean rightIdNumber(String idnumber) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = false;
        char[] idnumbers = idnumber.toCharArray();
        int length = idnumber.length();
        if (length == 18) {//长度为18
            flag1 = true;
        }
        if (idnumbers[0] == '0') {//第一位不能是0
            flag2 = false;
        }
        for (int i = 0; i < idnumbers.length - 1; i++) {//前17为必须是数字
            if (!(idnumbers[i] >= '0' && idnumbers[i] <= '9')) {
                flag3 = false;
            }
        }
        //最后一位可以是数字也可以是大小写x
        if ((idnumbers[(idnumbers.length - 1)] >= '0' && idnumbers[(idnumbers.length - 1)] <= '9')
                || idnumbers[(idnumbers.length - 1)] == 'x' || idnumbers[(idnumbers.length - 1)] == 'X') {
            flag4 = true;
        }
        flag = flag1 && flag2 && flag3 && flag4;
        return flag;
    }

    //方法：验证手机号的正确性
    public static boolean rightTel(String tel) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = true;
        boolean flag3 = true;
        int length = tel.length();
        char[] tels = tel.toCharArray();
        if (length == 11) {
            flag1 = true;
        }
        if (tels[0] == '0') {
            flag2 = false;
        }
        for (int i = 0; i < tels.length; i++) {
            if (!(tels[i] >= '0' && tels[i] <= '9')) {
                flag3 = false;
            }
        }
        flag = flag1 && flag2 && flag3;
        return flag;
    }

    //忘记密码
    public static void forgot(ArrayList<User> users,ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入您的用户名:");
        String name = sc.next();
        if(!containsUser(users,name)){
            System.out.println("该用户名不存在，未注册，请先注册！");
            enter(users,students);
        }
        System.out.println("请输入您的身份证号码:");
        String idnumber = sc.next();
        System.out.println("请输入您的手机号码:");
        String tel = sc.next();
        //判断输入是否正确
        int index=indexForUser(users,name);
        boolean flag1 = false;
        boolean flag2 = false;
        flag1=samePassword(idnumber,users.get(index).getIdnum());
        flag2=samePassword(tel,users.get(index).getTel());
        boolean flag=flag1&&flag2;
        while(flag){
            System.out.println("请设置新的密码:");
            String password1 = sc.next();
            System.out.println("请确认密码:");
            String password2 = sc.next();
            if(samePassword(password1,password2)){
                users.get(index).setKey(password2);
                System.out.println("修改密码成功！");
                enter(users,students);break;
            }else{
                System.out.println("输入密码不一致，修改失败，重新设置!");
            }
        }
        if(!flag){
            System.out.println("账号信息不匹配，修改失败！");
            enter(users,students);
        }
    }

    //添加学生界面
    public static ArrayList<Student> addStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        Student newStudent = new Student();

        //id进行唯一标识

        System.out.print("请输入学生id:");
        String id = sc.next();
        if (contains(students, id)) {
            System.out.println("学生id已经存在，添加失败");
            //返回主菜单
            System.out.println("输入任意键返回主菜单");
            String back = sc.next();
            menu(students);
        } else {
            newStudent.setId(id);
            System.out.print("请输入学生姓名:");
            String name = sc.next();
            newStudent.setName(name);
            System.out.print("请输入学生年龄:");
            int age = sc.nextInt();
            newStudent.setAge(age);
            System.out.print("请输入学生家庭住址:");
            String address = sc.next();
            newStudent.setAddress(address);
        }
        students.add(newStudent);
        System.out.println("添加学生成功！");
        System.out.println("输入任意键返回主菜单");
        String back = sc.next();
        menu(students);
        return students;
    }

    //判断学生集合是否包含某学号的学生
    public static boolean contains(ArrayList<Student> students, String id) {
        boolean flag = false;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    //根据学生id查找学生索引
    public static int index(ArrayList<Student> students, String id) {
        int index = -1;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    //删除学生
    public static ArrayList<Student> deleteStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要删除的学生id:");
        String id = sc.next();
        if (contains(students, id)) {
            int index = index(students, id);
            students.remove(index);
            System.out.println("删除成功！");
            System.out.println("输入任意键返回主菜单");
            String back = sc.next();
            menu(students);
        } else {
            System.out.println("找不到该id的学生，删除失败！");
            System.out.println("输入任意键返回主菜单");
            String back = sc.next();
            menu(students);
        }
        return students;
    }

    //修改学生
    public static ArrayList<Student> setStudent(ArrayList<Student> students) {
        Scanner sc = new Scanner(System.in);
        Student newStudent = new Student();
        System.out.print("请输入要修改的学生id:");
        String id = sc.next();
        if (contains(students, id)) {
            newStudent.setId(id);
            System.out.print("请输入学生姓名:");
            String name = sc.next();
            newStudent.setName(name);
            System.out.print("请输入学生年龄:");
            int age = sc.nextInt();
            newStudent.setAge(age);
            System.out.print("请输入学生家庭住址:");
            String address = sc.next();
            newStudent.setAddress(address);
            //根据索引修改学生信息
            int index = index(students, id);
            students.set(index, newStudent);
            System.out.println("修改成功！");
            System.out.println("输入任意键返回主菜单");
            String back = sc.next();
            menu(students);
        } else {
            System.out.println("找不到该id的学生，修改失败！");
            System.out.println("输入任意键返回主菜单");
            String back = sc.next();
            menu(students);
        }
        return students;
    }

    //查询学生
    public static ArrayList<Student> selectStudent(ArrayList<Student> students) {
        /*
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入要查询的学生id:");
        String id = sc.next();
        if(contains(students,id)){
            int index = index(students,id);
            Student stu=students.get(index);
            System.out.println(stu.toString());
            System.out.println("输入任意键返回主菜单");
            String back=sc.next();
            menu(students);
        }else{
            System.out.println("找不到该id的学生，查询失败！");
            System.out.println("输入任意键返回主菜单");
            String back=sc.next();
            menu(students);
        }
        return students;
    }

         */
        //打印所有学生信息
        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i).toString());
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("输入任意键返回主菜单");
        String back = sc.next();
        menu(students);
        return students;
    }
}