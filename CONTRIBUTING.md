Contributions are welcome!

## Contribution process for all committers
### Typos
If you have push access to the repository you can fix them directly, otherwise just make a pull request.

### Code
1) fork the repository
2) produce production code and unit tests
3) make a pull request
4) request review from `commercetools/java-admins`, who will review and optionally merge

Note: On bigger effort changes, open an issue and ask if you can/should/need to help.

## Requirements for a pull request
We want to have a clear and well-tested code base.

- change as few lines as necessary, do not mix up concerns
- avoid reformatting code, we want in diffs only real changes, except if it's a pure refactoring pull request
- mimic the code format and concepts of the rest of the code
- your committed code should not emit warnings such as unchecked generics
- you need to use integration tests against a CT project to prove that your code works
- your code is from you and not copied from third party sources
- use good [commit messages](https://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html)

Note: Integration tests will fail since we use encrypted credentials which are not available in forks for security reasons.

## Testing

Be aware that during integration tests, the CT platform will fail silently on certain errors, such as:
- wrong field name (unless required)
- wrong expansion path
- wrong query predicate
- wrong sort criteria
- wrong facet/filter expressions

Therefore you need to assert that the result is as expected. For example:
```java
public void testExpansionPath() {
  Product product = sphereClient.executeBlocking(ProductByIdGet.of(id).plusExpansionPaths(ExpansionPath.of("productType")));
  // make sure that the product type is indeed expanded
  assertThat(product.getProductType.getObj).isNotNull();
}
```

Also when testing, make sure that you have a clear distinction between test cases, so that you are not asserting the wrong case. For example:
