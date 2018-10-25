package com.dtl.myRpc.myRpcclient;

import com.dtl.myRpc.myRpcclient.client.ClientConfiguration;
import com.dtl.myRpc.myRpcclient.client.ClientListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyRpcClientApplication implements CommandLineRunner {

	@Autowired
	private ClientListener clientListener;

	public static void main(String[] args) {
		SpringApplication.run(MyRpcClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		clientListener.start();
	}
}
