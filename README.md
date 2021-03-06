# Ktx Preferences

[![Maven Central][mavenbadge-svg]][mavencentral] [![Travis (.org) branch][travisci-svg]][travisci] [![API][apibadge-svg]][apioverview] [![GitHub][license-svg]][license]

This library incorporates annotation processing to ensure the compile time verification for user-defined shared
preferences.

## Usage

### Add dependencies

Add dependencies to the *Kotlin-based* project:

```groovy
dependencies {
    implementation "co.windly:ktx-prefs:1.5.0"
    kapt "co.windly:ktx-prefs-compiler:1.5.0"
}
```

### Define shared preferences

Use the `@Prefs` annotation on any POJO. All (non static) fields will be considered a preference.

Minimal example:

```kotlin
@Prefs(value = "UserCachePreferences")
class UserCache(

  //region Id

  @DefaultLong(value = 0L)
  internal val id: Long,

  //endregion

  //region Name

  @DefaultString(value = "")
  internal val firstName: String,

  @DefaultString(value = "")
  internal val lastName: String,

  @DefaultString(value = "")
  internal val password: String,

  //endregion

  //region Active

  @DefaultBoolean(value = false)
  internal val active: Boolean

  //endregion
)
```

Accepted shared preference field types are:

* Boolean
* Float
* Integer
* Long
* String

### Use generated wrapper class

A class named `<YourClassName>Prefs` will be generated in the same package (at compile time).  Use it like this:

```kotlin
// Get access to shared preferences wrapper.
val cache = UserCachePrefs.get(this)

// ...or using an extension method.
// val cache = requireUserCache()

// Put a single value (apply() is automatically called).
cache
  .putId(1L)

// Put several values in one transaction.
cache
  .edit()
  .putFirstName("John")
  .putLastName("Snow")
  .putPassword("WinterIsComing")
  .putActive(true)
  .commit()

// Check if a value is set.
if (cache.containsFirstName()) {
  Log.d(TAG, "First name is set.")
}

// Access preferences one by one.
Log.d(TAG, "id -> " + cache.getId())
Log.d(TAG, "first name -> " + cache.getFirstName())
Log.d(TAG, "last name -> " + cache.getLastName())
Log.d(TAG, "password -> " + cache.getPassword())
Log.d(TAG, "active -> " + cache.isActive())

// Access all preferences.
Log.d(TAG, "cache -> " + cache.getAll())

// Remove a value.
cache.removeFirstName()

// Clear all preferences.
cache.clear()
```

### Extension methods

For all classes annotated with `@Prefs` there would be `<YourClassName>Ext` file generated which contains
two extension methods that simplifies accessing generated shared preferences wrapper.

Above mentioned extension methods will be called `require<YourClassName>()` like:

```kotlin
inline fun Context.requireUserCache(): UserCachePrefs =
    UserCachePrefs.get(this)

inline fun Fragment.requireUserCache(): UserCachePrefs =
    requireContext().requireUserCache()
``` 

### Reactive Extensions

Library supports generation of reactive methods by default. You can disable this feature either by:

- annotating class with `@Reactive(value = false)`,
- annotating field with `@Reactive(value = false)`

All shared property changes are emitted to given stream using `distinctUntilChanged()` method. You can configure this
behavior in `@Reactive` annotation (property `distinctUntilChanged`) for entire class or for each field separately.

## License

    Copyright 2019 Tomasz Dzieniak

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[apibadge-svg]: https://img.shields.io/badge/API-14%2B-brightgreen.svg?color=97ca00
[apioverview]: https://developer.android.com/about/versions/android-4.0
[license-svg]: https://img.shields.io/github/license/tommus/ktx-prefs.svg?color=97ca00
[license]: http://www.apache.org/licenses/LICENSE-2.0
[mavenbadge-svg]: https://img.shields.io/maven-central/v/co.windly/ktx-prefs.svg?color=97ca00
[mavencentral]: https://search.maven.org/artifact/co.windly/ktx-prefs
[travisci-svg]: https://img.shields.io/travis/tommus/ktx-prefs/master.svg?color=97ca00
[travisci]: https://travis-ci.org/tommus/ktx-prefs
