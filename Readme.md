### Simple Jersey/Grizzly Server
 This http server uses jersey and grizzly framework to be built.
 It is built on top of javax.rs library and uses that for the endpoint 
 functionlity.
 
 
### Endpoints

This project contains one endpoint. hello/world/{name}?lastName={lastName}
The queryParameter(lastName) is optional.
It is a get endpoint that returns a json string saying hello to the Name/lastName

### Models
HelloWorldModel is an API model using jackson library to define its json variables.
It is then put into a objectMapper(jackson) and written to a String to returned as a json string.
The response to the user will therefor be '{"message": "hello {name} {lastName}"}'


### App
Initializes the dependencies for the server.
Loads up the resource config for all the endpoint classes(services) that exist.
This is where we would load up a dependency for a database if we had one.
Then pass it along in a constructor to the classes that need it.

### Server
Starts the server itself, has the main method so the server can be run.
Will continue running until CTRL-C is pressed or an unexpected exception comes up.
