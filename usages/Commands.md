# Commands

Without KStom:

```kotlin
addSyntax({ sender, args ->

}, argument)
```

With KStom:

```kotlin
addSyntax(argument) {
    
}
```

-----------

Without KStom:

```kotlin
setDefaultExecutor { sender, _ ->

}
```

With KStom:

```kotlin
addSyntax { sender ->
    
}
```