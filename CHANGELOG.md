# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [3.21.8] - 2024-06-05
### :bug: Bug Fixes
- [`690f72e`](https://github.com/InseeFr/Eno/commit/690f72e341e3765b446942015ac71f5599e92551) - lunatic question numbering *(PR [#1023](https://github.com/InseeFr/Eno/pull/1023) by [@nsenave](https://github.com/nsenave))*
- [`383fa88`](https://github.com/InseeFr/Eno/commit/383fa886e6404d0fe317bc590720a50458227c88) - lunatic dropdown label type *(PR [#1024](https://github.com/InseeFr/Eno/pull/1024) by [@nsenave](https://github.com/nsenave))*
- [`f785cc4`](https://github.com/InseeFr/Eno/commit/f785cc498f2cf2bdbc321a97f7ef90624c2125aa) - suggester specific treatment dsfr *(PR [#1025](https://github.com/InseeFr/Eno/pull/1025) by [@nsenave](https://github.com/nsenave))*


## [3.21.5] - 2024-05-21
### :sparkles: New Features
- [`80e8e8a`](https://github.com/InseeFr/Eno/commit/80e8e8a922b7a7bfadf9f74eec7ca8ebef93885d) - lunatic dsfr question component *(PR [#987](https://github.com/InseeFr/Eno/pull/987) by [@nsenave](https://github.com/nsenave))*
- [`f40f0d4`](https://github.com/InseeFr/Eno/commit/f40f0d4e58de742c4c446062fed41802cef3efe8) - duration component *(PR [#991](https://github.com/InseeFr/Eno/pull/991) by [@nsenave](https://github.com/nsenave))*
- [`4c6141d`](https://github.com/InseeFr/Eno/commit/4c6141d511e0b8c37cfa1c0e9a6a1ecb5a445216) - lunatic variables dimension *(PR [#1001](https://github.com/InseeFr/Eno/pull/1001) by [@nsenave](https://github.com/nsenave))*
- [`a6cfea5`](https://github.com/InseeFr/Eno/commit/a6cfea5b2693f4b085f15499f457ff71978e0b11) - generate description for lunatic input numbers *(PR [#1004](https://github.com/InseeFr/Eno/pull/1004) by [@nsenave](https://github.com/nsenave))*
- [`1c680f8`](https://github.com/InseeFr/Eno/commit/1c680f82ab0363bfc0015c9595173b8d1f8ca828) - move declaration to description for dsfr sequences *(PR [#1005](https://github.com/InseeFr/Eno/pull/1005) by [@nsenave](https://github.com/nsenave))*

### :bug: Bug Fixes
- [`7c47c71`](https://github.com/InseeFr/Eno/commit/7c47c71b8981569501e49b9fd10c71e8462389c2) - question component filter *(commit by [@nsenave](https://github.com/nsenave))*
- [`4cee633`](https://github.com/InseeFr/Eno/commit/4cee63367ae40818e1b10565989a053488ecdc74) - null pointer exception in "dsfr" processing for pairwise *(PR [#1002](https://github.com/InseeFr/Eno/pull/1002) by [@nsenave](https://github.com/nsenave))*

### :recycle: Refactors
- [`a595009`](https://github.com/InseeFr/Eno/commit/a5950099f449f68fd471407fbb5b8d4881d4f7b2) - arrow char parameter false for CAWI mode *(commit by [@nsenave](https://github.com/nsenave))*

### :white_check_mark: Tests
- [`6820b2c`](https://github.com/InseeFr/Eno/commit/6820b2cbc19ec0bba29b21809eba11a0ea026dff) - improve memory management *(commit by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`069bc77`](https://github.com/InseeFr/Eno/commit/069bc77db0cf4b52f1db2b86338f14eca4a48d02) - bump io.spring.dependency-management from 1.1.4 to 1.1.5 *(PR [#999](https://github.com/InseeFr/Eno/pull/999) by [@dependabot[bot]](https://github.com/apps/dependabot))*


## [3.19.4] - 2024-04-24
### :sparkles: New Features
- [`92d7c21`](https://github.com/InseeFr/Eno/commit/92d7c21e0477c4d7d3b5209d508b760b6adf814a) - other specify modality *(PR [#980](https://github.com/InseeFr/Eno/pull/980) by [@nsenave](https://github.com/nsenave))*
- [`0732daf`](https://github.com/InseeFr/Eno/commit/0732daf87be05c778fe75b2f2672b17eb8a62544) - add cors for Eno-WS *(PR [#973](https://github.com/InseeFr/Eno/pull/973) by [@laurentC35](https://github.com/laurentC35))*
  - :arrow_lower_right: *addresses issue [#867](https://github.com/InseeFr/Eno/issues/867) opened by [@laurentC35](https://github.com/laurentC35)*
- [`0cd00e6`](https://github.com/InseeFr/Eno/commit/0cd00e6859a18cd128996071f484b7050f476b43) - restore controls criticality *(PR [#985](https://github.com/InseeFr/Eno/pull/985) by [@nsenave](https://github.com/nsenave))*
- [`c48be9c`](https://github.com/InseeFr/Eno/commit/c48be9c93d18a5929dba9c828b109dfef23a0e71) - dynamic table line controls *(PR [#986](https://github.com/InseeFr/Eno/pull/986) by [@nsenave](https://github.com/nsenave))*

### :bug: Bug Fixes
- [`9dc1480`](https://github.com/InseeFr/Eno/commit/9dc1480095469e6049e294ec231ae2ea6c5fdd80) - resizing pairwise variables size *(PR [#965](https://github.com/InseeFr/Eno/pull/965) by [@nsenave](https://github.com/nsenave))*
- [`0622ec5`](https://github.com/InseeFr/Eno/commit/0622ec5aee8102769c9de077ce4b065332346990) - lunatic non blocking consistency controls *(commit by [@nsenave](https://github.com/nsenave))*

### :white_check_mark: Tests
- [`2beeaf5`](https://github.com/InseeFr/Eno/commit/2beeaf59353d9cbb71553b15ff44174415bcb00d) - pogues source of some ddi used in tests *(commit by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`bfe211a`](https://github.com/InseeFr/Eno/commit/bfe211a654d65ed01889ca11fd882a1f52d1531d) - bump org.springdoc:springdoc-openapi-starter-webflux-ui *(PR [#972](https://github.com/InseeFr/Eno/pull/972) by [@dependabot[bot]](https://github.com/apps/dependabot))*
- [`0fbcbc4`](https://github.com/InseeFr/Eno/commit/0fbcbc4c6e84fdef159a9b4bd042bb00cfb439e2) - bump org.springframework.boot from 3.2.4 to 3.2.5 *(PR [#983](https://github.com/InseeFr/Eno/pull/983) by [@dependabot[bot]](https://github.com/apps/dependabot))*


## [3.18.5] - 2024-04-05
### :bug: Bug Fixes
- [`2a108fb`](https://github.com/InseeFr/Eno/commit/2a108fbbc23d932d1de14644685293ff87f48682) - duplicate declarations in lunatic pairwise component *(PR [#941](https://github.com/InseeFr/Eno/pull/941) by [@nsenave](https://github.com/nsenave))*
- [`194a9c4`](https://github.com/InseeFr/Eno/commit/194a9c4f8625ea8521d1b20934c9f86867978fdd) - ddi before question declarations insertion *(PR [#948](https://github.com/InseeFr/Eno/pull/948) by [@nsenave](https://github.com/nsenave))*
- [`ad41a45`](https://github.com/InseeFr/Eno/commit/ad41a45760c982b53510cb68945295240bef2dc4) - lunatic variables serialization *(PR [#943](https://github.com/InseeFr/Eno/pull/943) by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`ac3d805`](https://github.com/InseeFr/Eno/commit/ac3d8057f8bd55b4b41e91d2dc3b3bd20344d414) - bump org.springframework.boot from 3.2.3 to 3.2.4 *(PR [#939](https://github.com/InseeFr/Eno/pull/939) by [@dependabot[bot]](https://github.com/apps/dependabot))*
- [`9c03e81`](https://github.com/InseeFr/Eno/commit/9c03e81f86b21c80ac879f446d858f1d3da076d5) - update dependencies *(PR [#947](https://github.com/InseeFr/Eno/pull/947) by [@nsenave](https://github.com/nsenave))*
- [`54f6e05`](https://github.com/InseeFr/Eno/commit/54f6e05f167042df026fd0a9e1bab4a36da04cb7) - lunatic-model 3.5.1 *(commit by [@nsenave](https://github.com/nsenave))*


## [3.18.2] - 2024-03-25
### :sparkles: New Features
- [`936084d`](https://github.com/InseeFr/Eno/commit/936084d88f69632b4f5ca45b02c43731eb927347) - suggester *(PR [#925](https://github.com/InseeFr/Eno/pull/925) by [@nsenave](https://github.com/nsenave))*
- [`1807758`](https://github.com/InseeFr/Eno/commit/1807758fbc5dccc50cecdd8ed9cbe60e6864a143) - business first page parameter *(PR [#935](https://github.com/InseeFr/Eno/pull/935) by [@nsenave](https://github.com/nsenave))*

### :recycle: Refactors
- [`779d9ae`](https://github.com/InseeFr/Eno/commit/779d9aedd79429f774d3a40e6861e4f58eb0f231) - more javadoc and little logging enhancement *(PR [#934](https://github.com/InseeFr/Eno/pull/934) by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`87f6c4e`](https://github.com/InseeFr/Eno/commit/87f6c4ec3edbe6e4e40910b3d6c53c68578743e4) - update dependencies *(PR [#933](https://github.com/InseeFr/Eno/pull/933) by [@nsenave](https://github.com/nsenave))*
- [`4892e6a`](https://github.com/InseeFr/Eno/commit/4892e6adf896c2d967763a355d441265e1e435f3) - update gradle build tool *(commit by [@nsenave](https://github.com/nsenave))*


## [3.17.3] - 2024-02-26
### :bug: Bug Fixes
- [`73151aa`](https://github.com/InseeFr/Eno/commit/73151aa57586c68766392add5d70fdd4e09fb95a) - lunatic scalar variable values *(PR [#918](https://github.com/InseeFr/Eno/pull/918) by [@nsenave](https://github.com/nsenave))*


## [3.17.2] - 2024-02-23
### :recycle: Refactors
- [`282a34d`](https://github.com/InseeFr/Eno/commit/282a34db5c652b5b1b93d6dd0278c8451cf560b1) - remove lunatic patches *(PR [#913](https://github.com/InseeFr/Eno/pull/913) by [@nsenave](https://github.com/nsenave))*

## [3.17.1] - 2024-02-07
### :bug: Bug Fixes
- [`0fd6dc1`](https://github.com/InseeFr/Eno/commit/0fd6dc1651a6655f3b7ee8cd5e2e8f8d65fb4199) - lunatic dynamic table variables *(PR [#903](https://github.com/InseeFr/Eno/pull/903) by [@nsenave](https://github.com/nsenave))*

### :recycle: Refactors
- [`de29cc2`](https://github.com/InseeFr/Eno/commit/de29cc2f4c877b30f26eeb7c82a55d2298a6de55) - **resizing**: update warning message *(commit by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`2476295`](https://github.com/InseeFr/Eno/commit/24762952cfe0c3e3f1dd1ed52f038d9abf171ea7) - bump com.networknt:json-schema-validator from 1.3.0 to 1.3.1 *(PR [#897](https://github.com/InseeFr/Eno/pull/897) by [@dependabot[bot]](https://github.com/apps/dependabot))*

## [3.17.0] - 2024-02-02
### :bug: Bug Fixes
- [`6282cb0`](https://github.com/InseeFr/Eno/commit/6282cb00724ff90787089f93ba1d4d263465abb4) - pairwise missing variable name *(PR [#892](https://github.com/InseeFr/Eno/pull/892) by [@nsenave](https://github.com/nsenave))*
- [`c09fd08`](https://github.com/InseeFr/Eno/commit/c09fd08414670a3fabf4c41370139fcf46b5f957) - missing variable values *(PR [#894](https://github.com/InseeFr/Eno/pull/894) by [@nsenave](https://github.com/nsenave))*
- [`303396b`](https://github.com/InseeFr/Eno/commit/303396b98a59507892b425387bedb8de42006bdc) - missing variables in non paginated loops *(PR [#895](https://github.com/InseeFr/Eno/pull/895) by [@nsenave](https://github.com/nsenave))*

### :recycle: Refactors
- [`2958230`](https://github.com/InseeFr/Eno/commit/29582309411cb559429a3acb02b187aeafd75de8) - **swagger**: make eno url property optional *(commit by [@nsenave](https://github.com/nsenave))*

### :construction_worker: Build System
- [`a80f667`](https://github.com/InseeFr/Eno/commit/a80f667ff8f3f6e7f53e645e4e3ffe274135f8e9) - bump actions/cache from 3 to 4 *(PR [#888](https://github.com/InseeFr/Eno/pull/888) by [@dependabot[bot]](https://github.com/apps/dependabot))*
- [`eb89b88`](https://github.com/InseeFr/Eno/commit/eb89b884d81789a2d90883d4c84857c6eb1df98c) - bump gradle/gradle-build-action from 2 to 3 *(PR [#887](https://github.com/InseeFr/Eno/pull/887) by [@dependabot[bot]](https://github.com/apps/dependabot))*
- [`8a9eba1`](https://github.com/InseeFr/Eno/commit/8a9eba1df1b4142baa3ffb79f8bb218c7dc7c778) - bump com.networknt:json-schema-validator from 1.2.0 to 1.3.0 *(PR [#882](https://github.com/InseeFr/Eno/pull/882) by [@dependabot[bot]](https://github.com/apps/dependabot))*

### :memo: Documentation Changes
- [`f6ce68c`](https://github.com/InseeFr/Eno/commit/f6ce68c319e6df77815f97eb55e285a25609dd21) - update readme after java 21 *(commit by [@nsenave](https://github.com/nsenave))*

## [3.16.1] - 2024-01-30
### :bug: Bug Fixes
- [`f487c48`](https://github.com/InseeFr/Eno/commit/f487c4863e439417f536b54f0302c183fbeb7ce4) - remove prefix in questionnaire id *(PR [#890](https://github.com/InseeFr/Eno/pull/890) by [@nsenave](https://github.com/nsenave))*
  - :arrow_lower_right: *fixes issue [#889](undefined) opened by [@romaintailhurat](https://github.com/romaintailhurat)*

### :construction_worker: Build System
- [`063e513`](https://github.com/InseeFr/Eno/commit/063e513a721b6b2d341a8d5a617945199fbcff63) - **release**: add github token to authorize auto commit *(commit by [@nsenave](https://github.com/nsenave))*

## [3.16.0] - 2024-01-29
### :construction_worker: Build System
- [`3a3b00c`](https://github.com/InseeFr/Eno/commit/3a3b00c6df38c3dc6c7e9cbd476770ce23792f03) - java 21 *(PR #879 by @nsenave)*

### :rocket: CI Workflow Changes
- [`0e339f5`](https://github.com/InseeFr/Eno/commit/0e339f5e5915378c947c3d0e133f3b50e59f19ef) - update release note action *(commit by @nsenave)*
- [`0c0a29e`](https://github.com/InseeFr/Eno/commit/0c0a29e1eb6d135f4317e467e78bc190f8d4da5b) - add dependabot config file *(commit by @nsenave)*
- [`ab1dd36`](https://github.com/InseeFr/Eno/commit/ab1dd36b58d1658fb7e918e8f797f84062e881e9) - write changelog file in release workflow *(commit by @nsenave)*
- [`a35ebf9`](https://github.com/InseeFr/Eno/commit/a35ebf9b1454e5b11d6cc2106c0aae287953eda4) - refine previous tag search regex *(commit by @nsenave)*
- [`6a7ffa9`](https://github.com/InseeFr/Eno/commit/6a7ffa95ae76f29a6d46df2bc2067cb1e9066060) - add 'latest' tag in docker publish *(commit by @nsenave)*

### :memo: Documentation Changes
- [`b3fe5d8`](https://github.com/InseeFr/Eno/commit/b3fe5d8e4d549b5a351359b131dd2a0392646c59) - init changelog file *(commit by @nsenave)*
- [`5e83285`](https://github.com/InseeFr/Eno/commit/5e8328565cfecce9d4f1a6d18e2b734a55365ee9) - add property to link release note in swagger *(commit by @nsenave)*

## [3.15.10] - 2024-01-27
### :recycle: Refactors
- [`b33ae13`](https://github.com/InseeFr/Eno/commit/b33ae13618510159cc82715d96525fa6c77d9176) - remove usage of deprecated Lunatic-Model method *(commit by @nsenave)*

### :memo: Documentation Changes
- [`8011be4`](https://github.com/InseeFr/Eno/commit/8011be4786cc9a604acd464d8c38e2a29f366c6e) - add javadoc on Eno parameters
- [`2eefa4b`](https://github.com/InseeFr/Eno/commit/2eefa4bc0c7a9c7941d395de5e634525be3d5cb0) - add javadoc in Eno questionnaire
- [`04f383f`](https://github.com/InseeFr/Eno/commit/04f383fafec8bd2cc6428b87c22231137a791ec2) - add javadoc in label objects
- [`84abca3`](https://github.com/InseeFr/Eno/commit/84abca3f064feb994475db46a88958983ea7bff1) - update javadoc in code list classes
- [`cf566bc`](https://github.com/InseeFr/Eno/commit/cf566bc957c2605c74b065096e30a47e19b8eccb) - add javadoc in binding reference class

### :construction_worker: Build System
- [`c133df3`](https://github.com/InseeFr/Eno/commit/c133df36edbcd2f05bcf0a6299dd102bb807c1c7) - bump org.springframework.boot from 3.2.0 to 3.2.2 (#871)
- [`39b1687`](https://github.com/InseeFr/Eno/commit/39b1687791bd68f62d7afe443ab933f24953e205) - bump com.networknt:json-schema-validator from 1.1.0 to 1.2.0 (#870)

## [3.15.9] - 2024-01-22
### :bug: Bug Fixes
- [`aad6fb4`](https://github.com/InseeFr/Eno/commit/aad6fb4e9cb898f40418d0c09b89250f84a689ff) - ddi expression references resolution *(PR #873 by @nsenave)*

## [3.15.8] - 2024-01-19
### :bug: Bug Fixes
- [`7f24b39`](https://github.com/InseeFr/Eno/commit/7f24b390bb26b7902ad7d4555ec8e5b4497c5a96) - lunatic shape from *(PR #869 by @nsenave)*
  - :arrow_lower_right: *fixes issue #864 opened by @AnneHuSKa*

## [3.15.7] - 2024-01-17
### :recycle: Refactors
- [`ad5078b`](https://github.com/InseeFr/Eno/commit/ad5078b64e1b9cb80212869778d6ac63bf6ce008) - null cases handling for ddi code lists *(PR #863 by @nsenave)*
  - :arrow_lower_right: *fixes issue #862 opened by @AnneHuSKa*

## [3.15.6] - 2024-01-17
### :bug: Bug Fixes
- [`53a7dce`](https://github.com/InseeFr/Eno/commit/53a7dce380fca6ce023dc78623cd26656e324b5b) - **controls**: info criticality everywhere *(PR #861 by @nsenave)*

## 3.15.5 - 2024-01-15

:tada: First production release of Eno _Java_.

:sparkles: Main feature: DDI to Lunatic transformation.

:recycle: Eno web API has been enhanced.

[3.15.6]: https://github.com/InseeFr/Eno/compare/3.15.5...3.15.6
[3.15.7]: https://github.com/InseeFr/Eno/compare/3.15.6...3.15.7
[3.15.8]: https://github.com/InseeFr/Eno/compare/3.15.7...3.15.8
[3.15.9]: https://github.com/InseeFr/Eno/compare/3.15.8...3.15.9
[3.15.10]: https://github.com/InseeFr/Eno/compare/3.15.9...3.15.10
[3.16.0]: https://github.com/InseeFr/Eno/compare/3.15.10...3.16.0
[3.16.1]: https://github.com/InseeFr/Eno/compare/3.16.0...3.16.1

[3.17.0]: https://github.com/InseeFr/Eno/compare/3.16.1...3.17.0

[3.17.1]: https://github.com/InseeFr/Eno/compare/3.17.0...3.17.1
[3.17.2]: https://github.com/InseeFr/Eno/compare/3.17.1...3.17.2
[3.17.3]: https://github.com/InseeFr/Eno/compare/3.17.2...3.17.3
[3.18.2]: https://github.com/InseeFr/Eno/compare/3.17.3...3.18.2
[3.18.5]: https://github.com/InseeFr/Eno/compare/3.18.2...3.18.5
[3.19.4]: https://github.com/InseeFr/Eno/compare/3.18.5...3.19.4
[3.21.5]: https://github.com/InseeFr/Eno/compare/3.19.4...3.21.5
[3.21.8]: https://github.com/InseeFr/Eno/compare/3.21.5...3.21.8