package com.jt.test.redis;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.core.serializer.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.jt.pojo.BasePojo;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;

import lombok.Data;

public class TestJson {
	@Test
	public void toJson() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100l);
		itemDesc.setItemDesc("商品描述信息!");

		String json = ObjectMapperUtil.toJSON(itemDesc);
		System.out.println(json);

		ItemDesc itemDesc2 = ObjectMapperUtil.toObject(json, ItemDesc.class);
	}

	@Test
	public void testList() throws IOException {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(100l);
		itemDesc.setItemDesc("商品描述信息!");
		ItemDesc itemDesc2 = new ItemDesc();
		itemDesc.setItemId(100l);
		itemDesc.setItemDesc("商品描述信息!");
		ItemDesc itemDesc3 = new ItemDesc();
		itemDesc.setItemId(100l);
		itemDesc.setItemDesc("商品描述信息!");

		ArrayList<ItemDesc> list = new ArrayList<ItemDesc>();
		list.add(itemDesc);
		list.add(itemDesc2);
		list.add(itemDesc3);

		String json = ObjectMapperUtil.toJSON(list);
		System.out.println(list);
		List<ItemDesc> list2 = ObjectMapperUtil.toObject(json, list.getClass());
		System.out.println(list2);
	}

	@Test
	public void ObjectTest() throws Exception {
		A a = new A(1, "123");
//		a.setA(1);
//		a.setB("你好!!!");
		ObjectMapper obj = new ObjectMapper();
		String json = obj.writeValueAsString(a);
		System.out.println(json + "---");
		A aa = obj.readValue(json, a.getClass());
		System.out.println(aa);
	}

}

class A {
	Integer a;
	String b;

	public A(Integer a, String b) {
		this.a = a;
		this.b = b;
	}

	public A() {

	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "A [a=" + a + ", b=" + b + "]";
	}
}
