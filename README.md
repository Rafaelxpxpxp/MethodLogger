# MethodLogger
API to log method information with a default format, here's how it will be logged.</br>
<i>Entering the method: </i>```m=toEntity a=UserDTO{currentLevel=2}```</br>
<i>Returning the method: </i>```m=toEntity r=UserEntity{id=73b4b356-53eb-4bda-968c-b53dfb8cf39b, currentLevel=2}```

Here is an exemple how to use it:
```java
  @MethodLogger(msgOnSuccess = "Saving entity", msgOnException = "failed to save entity")
    public void save(
            E entity) {
        repository.save(entity);
    }
```

Sometimes you don't wanna log all params, maybe because it's sensitive information. Here's an exemple how to hide params:

```java
  @MethodLogger( logAllArgs = false)
    public void doSomething(
            E sensitiveInformation
            @ArgumentLogger
            T planInformation) {
        repository.save(entity);
    }
```

In this case only planInformation will be logged.

By default the method return will be logged, but you can hide it as well by setting ```logReturn=false```.
