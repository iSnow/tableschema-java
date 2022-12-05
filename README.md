# tableschema-java

[![Build Status](https://travis-ci.org/frictionlessdata/tableschema-java.svg?branch=master)](https://travis-ci.org/frictionlessdata/tableschema-java)
[![Coverage Status](https://coveralls.io/repos/github/frictionlessdata/tableschema-java/badge.svg?branch=master)](https://coveralls.io/github/frictionlessdata/tableschema-java?branch=master)
[![License](https://img.shields.io/github/license/frictionlessdata/tableschema-java.svg)](https://github.com/frictionlessdata/tableschema-java/blob/master/LICENSE)
[![Release](https://img.shields.io/jitpack/v/github/frictionlessdata/tableschema-java)](https://jitpack.io/#frictionlessdata/tableschema-java)
[![Codebase](https://img.shields.io/badge/codebase-github-brightgreen)](https://github.com/frictionlessdata/tableschema-java)
[![Support](https://img.shields.io/badge/support-discord-brightgreen)](https://discordapp.com/invite/Sewv6av)

A Java library for working with Table Schema. Snapshots on [Jitpack](https://jitpack.io/#frictionlessdata/tableschema-java).
tableschema-java is a library aimed at parsing CSV and JSON-Array documents into a tabular format according 
to [Table Schema](https://frictionlessdata.io/specs/table-schema/), a format definition based on 
[JSON Schema](https://json-schema.org/understanding-json-schema/).

It allows you to read and write tabular data with assurances to format integrity (it also allows reading and writing
CSV free-form, ie. without a Schema).

## Usage
- [Reading data](docs/table-reading.md) explains various ways of reading data
  - [Via Java Beans](docs/table-reading.md#reading-tabular-data-the-java-way) via a Java Bean class
  - [Without a Schema](docs/table-reading.md#reading-tabular-data-without-a-schema) Read as Object arrays and without 
         format integrity assurance
  - [With a Schema](docs/table-reading.md#reading-tabular-data-using-a-schema) Read as converted Java object arrays with
    format integrity assurance
- [Writing data](docs/table-writing.md) explains various ways of writing data
- [Creating a Schema](docs/creating-schemas.md) shows ways of creating a Table Schema from scratch or from example data
- [Datapackages](https://github.com/frictionlessdata/datapackage-java) documentation on Datapackages to distribute 
    data sets as self-contained units
- [Key contraints](docs/key-constraints.md) details ways of working with key constraints
- Validating data: Parsing string data to Field values (casting)
    - [Row Casting](docs/casting.md#row-casting) to check String arrays against a Schema
    - [Field Casting](docs/casting.md#field-casting) to check Strings  against a Field definition


## Contributing

Found a problem and would like to fix it? Have that great idea and would love to see it in the repository?

> Please open an issue before you start working.

It  saves a lot of time for everyone, and we are super happy to answer questions and help you along the way. 
Furthermore, feel free to join [frictionlessdata Gitter chat room](https://gitter.im/frictionlessdata/chat) 
and ask questions.

This project follows the [Open Knowledge International coding standards](https://github.com/okfn/coding-standards).

Get started:
```sh
# install jabba and maven2
$ cd tableschema-java
$ jabba install 1.8
$ jabba use 1.8
$ mvn install -DskipTests=true -Dmaven.javadoc.skip=true -B -V
$ mvn test -B
```

Make sure all tests pass.
