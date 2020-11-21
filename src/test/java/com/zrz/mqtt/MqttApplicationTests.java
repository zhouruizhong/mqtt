package com.zrz.mqtt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class MqttApplicationTests {

    @Test
    void contextLoads() {
    }

  public static void main(String[] args) {
    //
      File file = new File("E:\\case");
      if (file.isDirectory()) {
          String [] list = file.list();
          assert list != null;
          for (String s : list) {
              System.out.println(s);
              String [] arr = s.split("-");
              String caseId = arr[0];
              String name = arr[1];
                System.out.println("update ");
          }
      }
  }
}
