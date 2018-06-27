package reactor;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lucifer
 * @do 流收集器的测试
 * @date 2018/06/27 19:45
 */
public class StreamCollectorTest {

    @Test
    public void test(){
        List<Student> students = Arrays.asList(new Student("小明", 3),
                                    new Student("小花", 4),
                                    new Student("小黑", 27),
                                    new Student("小明", 20));
        //得到所有学生年龄的列表
        List<Integer> ages = students.stream().map(Student::getAge).collect(Collectors.toList());
        //Set<Integer> age = students.stream().map(Student::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println("当前学生的年龄集合：");
        System.out.println(ages);
        //统计汇总信息
        //尽量使用方法引用 Student::getAge不使用 e -> e.getAge, 因为后者会多生成一个方法lamad$0
        IntSummaryStatistics summaryStatistics = students.stream().collect(Collectors.summarizingInt(Student::getAge));
        System.out.println(summaryStatistics);
        //分块
        Map<Boolean, List<Student>> map = students.stream().collect(Collectors.partitioningBy(s -> s.getName().equals("小明")));
        System.out.println(map);
        //分组
        Map<String, List<Student>> studentMap = students.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(studentMap);
        //双层计数
        Map<String, Long> countMap = students.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        System.out.println(countMap);
    }


}

class Student{

    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
