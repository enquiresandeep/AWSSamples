package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.event.S3EventNotification;

/**
 * AWS Lambda function with S3 trigger.
 * 
 */
public class App {
	
	static final Logger log = LoggerFactory.getLogger(App.class);

	public Object handleRequest(Request request, Context context){
		AmazonDynamoDB adb = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(adb);
		Student student = null;
		switch (request.getHttpMethod()) {
			
			case "GET" :
				student = mapper.load(Student.class,request.getId());
				if (student == null) {
					throw new ResourceNotFoundException("Resource Not Found "+request.getId());
				}
				return student;
			case "POST" :
				student = request.getStudent();
				mapper.save(student);
				return student;
			case "default" :
				break;
				
		}
		
		return null;
	}

}

/**
{
"Records": [
    {
        "awsRegion": "us-east-1",
        "eventName": "ObjectCreated:Put",
        "eventSource": "aws:s3",
        "eventTime": "2018-09-01T15:35:42.355Z",
        "eventVersion": "2.0",
        "requestParameters": {
            "sourceIPAddress": "139.5.48.135"
        },
        "responseElements": {
            "x-amz-id-2": "ouuqLMI4XGqHtZSbtylmHjj1djmCmkXhNnRp8XMyKPNT9xx5x+8bVWtz7PWzgLaz4U6CIEhCWQo=",
            "x-amz-request-id": "FCB3F1B77DF4FF2B"
        },
        "s3": {
            "configurationId": "74eab48d-723f-43bb-b03c-a70432beff3c",
            "bucket": {
                "name": "trinity-test-lambda",
                "ownerIdentity": {
                    "principalId": "AWG8WXZYQB42H"
                },
                "arn": "arn:aws:s3:::trinity-test-lambda"
            },
            "object": {
                "key": "test.txt",
                "size": 13,
                "eTag": "a5decd745d43af4aa8cf62eef5be43ac",
                "versionId": "",
                "sequencer": "005B8AB1CE50E37A6D",
                "urlDecodedKey": "test.txt"
            },
            "s3SchemaVersion": "1.0"
        },
        "userIdentity": {
            "principalId": "AWG8WXZYQB42H"
        }
    }
]
}

{
"httpMethod":"$context.httpMethod",
"id":$input.params{'id'} 
}



*/