package com.wsyt.spark.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class JavaWordCount {

    public static void main(String[] args) {
        SparkConf conf = new SparkConf();

        conf.setAppName("JavaWordCount");
        conf.setMaster("local");

        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> fileRDD = jsc.textFile("data/word_count.txt");

        JavaRDD<String> words = fileRDD.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

        //JavaRDD<Tuple2<String, Integer>> wordPair = words.map(s -> new Tuple2<>(s, 1));
        //此处应该调用mapToPair方法,而不应该是用map方法
        JavaPairRDD<String, Integer> wordPair = words.mapToPair(word -> new Tuple2<>(word, 1));

        JavaPairRDD<String, Integer> result = wordPair.reduceByKey(Integer::sum);

        //Task not serializable,NotSerializableException
        //result.foreach(System.out::println);
        //以上代码将会报错,转而使用以下方式
        result.foreach(res -> System.out.println(res));
    }
}
