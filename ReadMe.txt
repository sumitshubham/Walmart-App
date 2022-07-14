Steps to run the application:
1. Import walmartUser, walmartWishList, server and the ApiGateway inside STS.
2. Right click project name in package explorer > maven > update project.

	* if faced with problem like "can't load main class" then:
		2.1 Right click project name in package explorer > maven > update project > select force snapshots > ok.

	* if faced with problem where java classes (like String, Util etc) aren't recognised then:
		2.1 remove JRE system libraries by going to configure build path.
		2.2 right click project name > build path > add libraries > add jre system libraries > then select workspace libraries (3rd 																							option)
		2.3 go to pom.xml change java version in properties from 11/17 to 1.8.
		2.4 Right click project name in package explorer > maven > update project.

3. Change mysql password in application.properties in walmartUser to machine's mysql password (Also change path as well, if its different than what is mention in the application.properties).
4. Run the server, api gateway, walmart user and walmart wishlist as springboot application. 
5. Open Frontend in VSCode and run "npm install node" to install node modules.
6. Run "ng serve" to launch the frontend application.
7.and hence your project is ready

