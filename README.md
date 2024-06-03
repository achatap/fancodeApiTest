# FanCode SDET Assignment

## **Overview:**
### Task : To Automate the Below Scenario.
#### Scenario :- All the users of City `FanCode` should have more than half of their todos task completed.
Given User has the todo tasks
And User belongs to the city FanCode
Then User Completed task percentage should be greater than 50%

#### Note :-
- You can use any language to write api automation/Framework.
- Fancode City can be identified by lat between ( -40 to 5) and long between ( 5 to 100) in users api

### **Some of the key features of this framework:**

1. It generates Extent report with all the step details. Report will be generated both HTML file format.
2. Generates execution logs, with detailed request and response details.
3. Test execution can be triggered form command line. 
4. Easy integration to CI/CD pipeline.

## **Required Setup :**

- [Java](https://www.guru99.com/install-java.html) should be installed and configured.
- [Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) should be installed and configured.
- Download the files from Git repository either as zip file OR using [Git](https://phoenixnap.com/kb/how-to-install-git-windows).

## **Running Test:**

Open the command prompt and navigate to the folder in which pom.xml file is present.
Run the below Maven command.

    mvn clean test


Once the execution completes report & log will be generated in below folder.

**Report:** 		*target/report*<br>
**Log:** 		*target/logs*