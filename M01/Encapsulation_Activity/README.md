# COMP 272 — Encapsulation Activity

## Files

```text
src/
├── app/
│   └── AccessLab.java
└── devices/
    └── Device.java
```

Compile:

```bash
javac -d out src/devices/Device.java src/app/AccessLab.java
```

Note: \'-d out tells\' javac to place the compiled .class files in the out directory, creating the appropriate package folders inside it.
      out is the directory chosen to store the compiled .class files.

Run:

```bash
java -cp out app.AccessLab
```

Note: -cp out tells java to search inside the out directory for the compiled classes and packages needed to run the program.

Test => make the requested change, uncomment the indicated call, compile, and run if compilation succeeds.

Record: Does it compile? If it compiles, does it run? What do you conclude?

## A — Same class

In `AccessLab.java`, use `secret()` and TEST 1 in `main()`.

1. Set `secret()` to `private`. Uncomment its call. Test.
2. Set `secret()` to package-private (Default Case). Test again.
3. Set `secret()` to `protected`. Test again.
4. Set `secret()` to `public`. Test again.

What do you conclude about access from the same class?

## B — Different class, same file

In `AccessLab.java`, use `LocalHelper` and its commented TEST 2 in main().

First, keep `message()` public and try to:

1. Keep `LocalHelper` package-private. Uncomment TEST 2, then test.
2. Set `LocalHelper` to `private`. Test again.
3. Set `LocalHelper` to `protected`. Test again.
4. Set `LocalHelper` to `public`. Test again.

Second, restore `LocalHelper` to package-private and try to:

1. Set `message()` to `public`. Test.
2. Set `message()` to package-private. Test.
3. Set `message()` to `protected`. Test again.
4. Set `message()` to `private`. Test.

What access levels can a top-level class have? Does being in the same file give another class access to private members?

## C — Different file and different package

In `AccessLab.java`, use TEST 3 in `main()`.

1. Set `status()` in `Device.java` to `public`. Test.
2. Set it to `private`. Test.
3. Set it to package-private. Test.
4. Set it to `protected`. Test.

What do you conclude about access from a different package?

## D — Different file, same package

1. Move `AccessLab.java` into `src/devices/`.
2. Change its first line to `package devices;`.
3. Remove `import devices.Device;`.
4. Compile: `javac -d out src/devices/Device.java src/devices/AccessLab.java`
5. Run: `java -cp out devices.AccessLab`
6. Test `status()` using TEST 3 as `public`, package-private, `protected`, and `private`.

What do you conclude about classes in the same package?

## E — Inheritance and `protected`

Move `AccessLab.java` back to `src/app/`, restore `package app;` and `import devices.Device;`, then:

1. Change its declaration to `public class AccessLab extends Device`.
2. Set `status()` to `protected`.
3. Uncomment the first call in TEST 4. Compile and run.
4. Comment it again. Uncomment the second call in TEST 4. Compile and run.

What do you conclude about calling a protected method from a subclass in a different package? Does the reference type matter?

## F — Inner classes

First, restore the declaration to `public class AccessLab` without `extends Device`.

Then, use TEST 5 in `AccessLab.java`.

1. Set `Screen` to `public`. Test.
2. Set `Screen` to package-private. Test.
3. Set `Screen` to `protected`. Test.
4. Set `Screen` to `private`. Test.

Second, uncomment TEST 6 in `Device.java` and the call to TEST 6 in `AccessLab.java`.

1. Set `Screen` to `public`. Test.
2. Set `Screen` to package-private. Test.
3. Set `Screen` to `protected`. Test.
4. Set `Screen` to `private`. Test.

What do you conclude about accessing an inner class from another class and from its enclosing class?
