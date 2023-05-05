* Dependancy Injection With Spring
    * SOLID Principles of OOP
      # S -> Single Reponsibility Principle
             / Every Class should have a single Responsibility
      	   / There should never be more than one reason for a class to change
      	   / Your Classes should be small.no more than a screen full of code
      	   / Avoid 'god' classes
      	   / Split big classes into smaller classes
      	   / My 2000 line method tested "Fine"
      # O -> Open Closed Principle
      	   / Your classes should be open for extension
      	   / But closed for modification
      	   / You should be able to extend a classes behavior , without modifiying it
      	   / Use private variables with getters , setters - ONLY when you need them
      	   / Use abstract base classes  
      # L -> Liskov Substitution Principles
      	   / Objects in a program would be replaceable with instances of their subtypes
      	     WITHOUT altering the correctness of the program
      	   / Violations will often fail the "Is a" test
      	   / A Square "is a" rectangle
      	   / However,a Rectangle 'is not' a square
      # I -> Interface Segregation Principle
      	   / Make fine grained interfaces that are client specific
      	   / Many client specific interfaces are better than on 'General Purpose' interface
      	   / Keep your components focused and minimize dependencies between them
      	   / Notice relationship to the single responsability Principle?
      	   / Le Avoid 'god' interfaces
      # D -> Dependancy inversion Principle
             / Abstractions should not depend upon details
      	   / Details should depend upon abstractions
      	   / Important that higher level and lower level obkects depend on the same abstract interaction
      	   / This is not the same as Dependancy injection - which is how objects obtain dependent objects

* The Spring Context
  NB : Even though we dont't have a web dependency on the classpath
  the spring web starter is not there so we don't have tomcat
  the sterotype Controller (@Controller) still will create
  this as spring bean
  Exemple :
  # Create a class and annotate it with @controller
  # Create a method in this controller (Simple Method for the test)
  # in the main class associate ApplicationContext to springapplication.run()

@SpringBootApplication
public class SpringDiApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(SpringDiApplication.class, args);
		MyController myController = ctx.getBean(MyController.class);
		System.out.println("I am Main Method");
		System.out.println(myController.sayHello());
	}

}

* Spring Test Context

@SpringBootTest
class SpringDiApplicationTests {

	@Autowired
	MyController myController;

	@Autowired
	ApplicationContext applicationContext;


	@Test
	void testAutoweiredOfController() {
		System.out.println(myController.sayHello());
	}

	@Test
	void testGetControllerFromCtx() {
		MyController myController1 = applicationContext.getBean(MyController.class);
		System.out.println(myController1.sayHello());
	}

	@Test
	void contextLoads() {
	}

}

* Basics of dependency injection
  # Dependency injection is where a needed dependency is injected by another object
  # The class being injected has no responsibility in instantiating the object being injected
  # Some say you avoid declaring obkects using 'new'
       a - not 100% correct
  	 b - Be Pragmatic in what is not being managed in the spring context

    + Concrete classes vs Interfaces
      # DI can be done with concrete classes or with interfaces
      # Generally DI with concrete classes shoul be avoided
      # DI via interfaces is highly preffered
            . Allows runtime to decide implementation to inject 
      	  . Follows interface segregation Priciple SOLID
      	  . Also, Makes your code more testable - Mocking becomes trivial
    + Iox vs Dependency Injection
      # Ioc and DI are easily confused
      # DI refers much to the composition of your classes
           . ie - you compose your classes with DI in mind
      . you might write code to 'inject' a dependency
      # Ioc is the runtime environment of your code
           . Control of Dependency Injection is inverted to the framework
      . Spring is in control of the injection of dependencies

    -> Best Practices with dependency injection
    # Favor using constructor injection over Sette injection
    # Use final property for injected components
    . Declare property private final and initialize in the constructor
    . Whenever practical , code to an interface

    -- Via Setter :

@Controller
public class UserController {
private UserService userService;

    // Setter injection for UserService
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // Define methods that use the userService object
    // ...
}

* Dependencies Without Injection

. Exemple :
@Controller
public class MyController {

    private final GreetingService greetingService;

    public MyController() {
        this.greetingService = new GreetingServiceImpl();
    }

    public String sayHello(){
        System.out.println("I am the controller");
        return greetingService.sayGreeting();
    }
}

. Test with jUnit5 :

class MyControllerTest {

    @Test
    void sayHello() {
        MyController myController = new MyController();
        myController.sayHello();
    }
}

So Right now this controller does not play nicely with spring FR
this is really the way that we do not want to be doing things
but i want to show you the concept here where we're talking a
dependency, External dependency that is ,
and this is one way that people might do it without the Spring FR
and without DI

* Dependency Injection without Spring
  . Property Injection :

public class PropertyInjectedController {
GreetingService greetingService;

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}

Test :

class PropertyInjectedControllerTest {

    PropertyInjectedController propertyInjectedController;

    @BeforeEach
    void setUp() {
        propertyInjectedController = new PropertyInjectedController();
        propertyInjectedController.greetingService = new GreetingServiceImpl();
    }

    @Test
    void sayHello() {
        System.out.println(propertyInjectedController.sayHello());
    }
}

	. Setter Injection : 

public class SetterInjectedController {

    private GreetingService greetingService;

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}

Test :

class SetterInjectedControllerTest {

    SetterInjectedController setterInjectedController;

    @BeforeEach
    void setUp() {
        setterInjectedController = new SetterInjectedController();
        setterInjectedController.setGreetingService(new GreetingServiceImpl());
    }

    @Test
    void sayHello() {
        System.out.println(setterInjectedController.sayHello());
    }
}

	. Constructor Injection :

public class ConstructorInjectedController {

    private final GreetingService greetingService;


    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello(){
        return greetingService.sayGreeting();
    }
}

Test :

class ConstructorInjectedControllerTest {

    ConstructorInjectedController controller;

    @BeforeEach
    void setUp() {
        controller = new ConstructorInjectedController(new GreetingServiceImpl());
    }

    @Test
    void sayHello() {
        System.out.println(controller.sayHello());
    }
}

* Dependency Injection Using Spring Boot :
  # Annotate it the service Implementatio By @Service
      . This one of the spring sterotypes that says Hi this is a spring component
  # Annotate the controller by @Controller (This now becomes a spring manages component)
  # Inject the service in the controller with @Autowired
  # Inject the controller to the test class
  # Add @SpringBootTest to the test class (tell J Unit that i want the spring context to be loaded ... )

Test :

@SpringBootTest
class MyControllerTestInjectionSpring {

    @Autowired
    MyController myController;

    @Test
    void sayHello() {
        myController.sayHello();
    }
}

* Primary Bean

  . Problem
  # Create Another Service that implement also tha same service
  # Inject This Service and use it
  (Spring foudn two service in our options and he don't which one use)
  . Solution
  # Use Primary Annotation @Primary

@Primary
@Service
public class GreetingServicePrimaryImpl implements GreetingService {
@Override
public String sayGreeting() {
return "Hello From The Primary Service";
}
}


* Using Qualifiers
* Spring Profiles




In Spring Framework, @Primary, @Qualifier, and @Profile are used to resolve dependencies between beans. Here's a brief explanation of each annotation:

     # @Primary :  The @Primary annotation is used to indicate a primary bean when there are multiple beans of the same type. 
				   When Spring needs to inject an instance of a particular type, it will choose the primary bean by default, 
				   unless a more specific bean is requested using @Qualifier.

	 # @Qualifier : The @Qualifier annotation is used to specify which bean to inject when there are multiple beans of the same type. 
				    When you have multiple beans of the same type, you can use @Qualifier with the name of the bean you want to inject.

     # @Profile :  The @Profile annotation is used to specify which beans are active based on the current active profile. 
				   When a profile is specified, beans that do not match the profile are not created or used. 
				   This is useful when you have multiple configurations for different environments, such as development, testing, and production.

In summary, @Primary and @Qualifier are used to resolve multiple beans of the same type, while @Profile is used to control which beans are active
based on the current active profile


* Spring Bean LifeCycle

  # Instantiation: A new instance of a bean is created using the
  specified class, either through a constructor or a factory method.

  # Dependency Injection: Any dependencies of the bean are injected
  into it, either through setters or constructors.

  # Initialization: Any initialization methods specified by the bean
  are called, such as a method annotated with @PostConstruct.

  # Usage: The bean is used to perform its intended function, such as
  servicing requests in a web application.

  # Destruction: When the container is shut down, any beans that
  implement the DisposableBean interface or have a method annotated with @PreDestroy are destroyed.

* Callback Interfaces
  # Spring has two interfaces you can implement for call back events
  # InitializingBean.afterPropertiesSet()
       . Called after properties are set
  # DisposableBean.detroy()
       . Called during bean destruction in shutdown

* LifeCycle Annotations
  # PostContruct annotated methods will be called after the bean has been
  constructed,bur before its returned to the requesting object
  # PreDestroy : is called just before the bean is destroyed by the container
