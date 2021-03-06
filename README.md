# Gradle plugin for Ecgine
This is a very simple Gradle plugin to deploy and test ecgine projects.


## Installing the plugin

Releases of this plugin are hosted at BinTray (http://bintray.com) and is part of jcenter repository.

Setup the plugin like this:


###  Gradle versions since 2.13

```groovy
buildscript {
	repositories {
		jcenter()
	}
	dependencies {
		classpath 'org.ecgine.gradle:gradle-ecgine-plugin:1.1.5'
	}
}

apply plugin: 'org.ecgine.gradle'
```

### Configure the plugin
####Step 1: Adding bundles(Required)

Add below closer with required bundles.

```groovy
ecgine{
	bundle 'ecgine.server.start_1.0.0'
	bundle 'ecgine.client.start_1.0.0'
}
```
####Step 2: Package Configuration(Required)

Set below properties to upload bundles and create package version.

```groovy
ecgine{
	pkg{
		name      'NAME'
		namespace 'NAME_SPACE'
		version   'VERSION'
		category  'CATEGORY'
		verticals 'VERTICALS'
	}
}	
```
Property | Description
-------- | -----------
name| Name of the package
namespace | Namespace of the package
version | Version of the package
category | Category of the package
verticals | Verticals of the package

####Step 3: Changing default values(Optional)

Property | Default Value | Description
-------- | ------------- | -----------
plugins | 'plugins' | A directory to strore all bundles.
setup | ${user.home}/.ecgine/setup | A directory to prepare client and server setups to run
url | 'https://vimukti.ecgine.com/' | An URL for ecgine repository.

Set below properties to change default values.
```groovy
ecgine{
	plugins 'LOCATION_TO_PLUGINS'
	setup 'LOCATION_TO_SETUP'
	url 'NEW_ECGINE_REPO_URL'
}
```

####Step 4: Running Configuration(Optional)

Property | Default Value | Description
-------- | ------------- | -----------
debugPort | 8000(client)/4000(server) | A port to debug client/server (0 for no debug)
consolePort | 2501(client)/2502(server) | A port to open osgi console (0 for no console)
ms | '64m' | Initial java heap space
mx | '1g' | Maximum java heap space
ss | none | Java thread stack size

Set below properties for client and server running configuration
```groovy
ecgine{
	client{
		debugPort CLIENT_DEBUG_PORT
		consolePort CLIENT_CONSOLE_PORT
	}
	server{
		debugPort SERVER_DEBUG_PORT
		consolePort SERVER_CONSOLE_PORT
	}
}
```


####Step 5: Master Configuration(Optional)

Property | Default Value | Description
-------- | ------------- | -----------
company | 'Test' | Name of the company
company | 'India' | Country of that company
email | 'test@master.com' | Email id to login
firstName | 'First' | First name of that user
lastName | 'Last' | Last name of that user
password | 'password' | A password to login
domain| 'master' | A subdomain

Set below properties to change master details
```groovy
ecgine{
	master{
		subDomain 'TEST_DOMAIN'
	}
}
```

### Using the plugin

####Step 1: Getting APIKEY
First you need to login with ecgine emailId and password, you will get an APIKEY.

Run below command to get apikey
> gradle ecgineLogin -Pemailid=emailId


####Step 2: Prepare bundles

Run below command to get all bundles from ecgine repository
> gradle ecginePrepare

Note :Add plugins directory in target platform to make available all ecgine bundles to build in eclipse.
Goto window -> preferences -> plug in development -> target platform ->Add


####Step 3: Run ecgine server

Run below command to start ecgine server
> gradle ecgineServerStart

####Step 4: Run ecgine client

Run below command to start ecgine client
> gradle ecgineClientStart

####Step 5: Deploy package

Run below command to deploy your package in ecgine repository
> gradle ecgineDeploy

