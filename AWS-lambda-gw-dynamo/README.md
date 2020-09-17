# AWSSamples
Sample Project with Dynamo-DB and Lambda Function Integration

i.Integration Mapping Template from APi to lambda (GET Request)

#set($inputRoot = $input.path('$'))
{
"httpMethod":"$context.httpMethod",
"id":$input.params('id') 
}
 
ii.	Integration Mapping Template from APi to lambda (POST Request)

#set($inputRoot = $input.path('$'))
{
"httpMethod":"$context.httpMethod",
"student":$input.json('$') 
}
