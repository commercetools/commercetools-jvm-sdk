# Sphere Play SDK

[![Build Status](https://travis-ci.org/commercetools/sphere-play-sdk.png)](https://travis-ci.org/commercetools/sphere-play-sdk)

This project contains two Java libraries for building shop applications on top of [Sphere](http://sphere.io):

* standalone Java client
* SDK for building online shops using the [Play Framework](http://www.playframework.com/), built on top of the Java client

## Building
This project is built using `sbt`. If you want to execute the tests run
```
sbt test
```

## License

The Java Client is licensed under the terms of the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). 
This means that you can use it in your commercial products.

## Dependencies

This library uses the following open-source libraries:

- [AsyncHttpClient](https://github.com/AsyncHttpClient/async-http-client)
- [Guava](https://code.google.com/p/guava-libraries/)
- [Joda time](http://joda-time.sourceforge.net/)
- [Jackson](http://jackson.codehaus.org/)
- [Apache Commons Codec](http://commons.apache.org/proper/commons-codec/)
- [nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

These libaries are all also under the Apache License.
