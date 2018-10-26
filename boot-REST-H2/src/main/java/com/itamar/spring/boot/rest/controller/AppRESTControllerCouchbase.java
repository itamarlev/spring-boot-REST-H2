package com.itamar.spring.boot.rest.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/couchbase")
@Api(value = "couchbase docs")
public class AppRESTControllerCouchbase {

	@ApiOperation(value = "returns a document from beer-sample bucket")
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = { "application/json", "application/xml", "text/xml" })
	String all() throws IOException {
		Cluster cluster = null;
		try {
			cluster = CouchbaseCluster.create();
			cluster.authenticate("itamar", "abc12345");
			Bucket beerSampleBucket = cluster.openBucket("beer-sample");
			JsonDocument doc = beerSampleBucket.get("21st_amendment_brewery_cafe");
			return doc.toString();
		} finally {
			cluster.disconnect();
		}
	}
}
