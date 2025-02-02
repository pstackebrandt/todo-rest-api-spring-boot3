# Test Comments

## Warnings during test run with gradle

### false warning: Mockito is self-attaching to enable the inline-mock-maker

This warning is an error by Mockito and can be ignored.

``` powershell
TodoListApiApplicationTests > initialState_ShouldContainThreeTodos() STANDARD_ERROR
    Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. Please add Mockito as an agent to your build what is described in Mockito's documentation: <https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#0.3>
```

 The warning about “Mockito is currently self-attaching … This will no longer work in future releases of the JDK” is also a known issue of Mockito’s “inline mock maker.” By adding mockito-inline as a runtime dependency, we automatically trigger that behavior. As long as tests do not fail due to this warning, you can ignore it or remove inline mocking from your environment. Possibly, a future version of Mockito or a future JDK update will do away with this warning.
 (ChatGPT o1 on 2025-02-01)
