/**
 * Copyright 2009-2019 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.learn;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.util.Properties;

class MybatisOperateTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/learn/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
      "org/apache/ibatis/learn/CreateDB.sql");
  }

  /**
   * Mybatis基础操作
   * {@link org.apache.ibatis.plugin.Interceptor#setProperties(Properties)}
   */
  @Test
  void testMybatisGeneralMetadata() {
    try (SqlSession sqlSession1 = sqlSessionFactory.openSession(); SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
      try {
        Mapper mapper1 = sqlSession1.getMapper(Mapper.class);
        Mapper mapper2 = sqlSession2.getMapper(Mapper.class);


        User user = new User();
        user.setId(1);
        user.setName("jy-zh");
        user.setId(1);
        mapper1.insertUser(user);
        sqlSession1.commit();
//        mapper1.getUser(1);
//        mapper1.getUser(1);

        mapper2.getUser(1);
        mapper2.getUser(1);


        mapper1.deleteUser(1);
      } finally {
//        sqlSession.commit();
      }
    } catch (Exception e) {
      Assertions.fail(e.getMessage());
    }
  }

}
