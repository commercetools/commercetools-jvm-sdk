# Sphere Play SDK

[![Build Status](https://travis-ci.org/commercetools/sphere-play-sdk.png)](https://travis-ci.org/commercetools/sphere-play-sdk)

[SPHERE.IO](http://sphere.io) is the first Platform-as-a-Service solution for eCommerce.

The Sphere Play SDK comprises of two Java libraries designed for building shop applications on top of SPHERE.IO:

* standalone Java client to access Sphere HTTP APIs
* SDK for building online shops using the [Play Framework](http://www.playframework.com/)

## Building
This project is built using `sbt`. If you want to execute the tests run
```
sbt test
```

## License

The Sphere Play SDK is released under the
[Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html). 

## Dependencies

This project uses the following open-source libraries:

- [AsyncHttpClient](https://github.com/AsyncHttpClient/async-http-client)
- [Guava](https://code.google.com/p/guava-libraries/)
- [Joda time](http://joda-time.sourceforge.net/)
- [Jackson](http://jackson.codehaus.org/)
- [Apache Commons Codec](http://commons.apache.org/proper/commons-codec/)
- [nv-i18n](https://github.com/TakahikoKawasaki/nv-i18n)

These libaries are all also released under the Apache 2.0 License.
