本篇文章讲解的内容是**MVC**、**MVP**、**MVVM**以及使用**MVVM**搭建**GitHub客户端**，以下是**框架**的**GitHub地址**：

**Dagger2**版本：[Dagger2](https://github.com/TanJiaJunBeyond/AndroidGenericFramework)

**Koin**版本：[Koin](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/tree/mvvm-koin)

在讲解之前，我想先聊一下**MVC**、**MVP**和**MVVM**相关的概念。

# MVC

**MVC（Model-View-Controller）**的概念最早源自于**Erich Gamma**、**Richard Helm**、**Raplph Johnson**、**John Vlissides**这四位大牛在讨论**设计模式**中的**观察者模式**时的想法；**Trygve Reenskaug**在**1979年5月**的时候发表了一篇文章叫做**Thing-Model-View-Editor**，这篇文章中虽然没提到**Controller**，但是他提到的**Editor**就是非常接近这个概念，7个月后，他在发表的一篇叫做**Models-Views-Controllers**中正式提出了**MVC**这个概念。

* **Model（数据层）**：负责处理**数据逻辑**。
* **View（视图层）**：负责处理**视图显示**，在**Android**中使用**xml**描述视图。
* **Controller（控制层）**：在**Android**中的**Activity**和**Fragment**承担此层的重任，负责处理**业务逻辑**。

这里要注意的是，**Activity**和**Fragment**并非是标准的**Controller**，因为它们不仅要负责处理**业务逻辑**，还要去**控制界面显示**，这样导致的结果是随着业务的复杂度不断提高，**Activity**和**Fragment**会变得非常**臃肿**，不利于代码的维护。

# MVP

**MVP（Model-View-Presenter）**是**MVC**进一步演化出来的，由**Microsoft**的**Martin Fowler**提出。

* **Model（数据层）**：负责处理**数据逻辑**。
* **View（视图层）**：负责处理**视图显示**，在**Android**中使用**xml**或者**Java/Kotlin**代码去实现视图，**Activity**和**Fragment**承担了此层的责任。
* **Presenter**：负责连接**Model**层和**View**层，是这两层的中间纽带，负责处理**业务逻辑**。

在**MVP**中，**Model**层和**View**层之间不能有交互，要通过**Presenter**层进行交互，其中**View**层和**Presenter**层是通过**接口**进行交互，可以定义**Contract（契约）**接口来指定**View**层和**Presenter**之间的**契约**，官方代码如下：

``` kotlin
interface AddEditTaskContract {

    interface View : BaseView<Presenter> {

        var isActive: Boolean

        fun showEmptyTaskError()

        fun showTasksList()

        fun setTitle(title: String)

        fun setDescription(description: String)

    }

    interface Presenter : BasePresenter {

        var isDataMissing: Boolean

        fun saveTask(title: String, description: String)

        fun populateTask()

    }

}
```

在**MVP**中，**View**层**不会部署任何的业务逻辑**，从而比较**薄**，它被称为**被动视图（Passive View）**，意思是它**没有任何的主动性**，而且这样的设计也方便做**单元测试**，但是也会有如下问题：

1. 尽管减少了**View**层的代码，但是随着业务的复杂度不断提高，**Presenter**层的代码也会变得越来越臃肿。
2. **View**层和**Presenter**层是通过**接口**交互的，随着业务的复杂度不断提高，接口数量会**大量增加**。
3. 如果**View**层更新的话，就像**UI的输入**和**数据的变化**，都需要主动去调用**Presenter**层的代码，缺乏**自动性**和**监听性**。
4. **MVP**是以**UI**和**事件**为驱动的**传统模型**，**更新UI**需要保证能持有**控件**的**引用**，而且**更新UI**需要考虑**Activity**或者**Fragment**的生命周期，防止**内存泄漏**。

# MVVM

**MVVM（Model-View-ViewModel）**是**MVP**进一步演化出来的，它也是由**Microsoft**的**Martin Fowler**提出。

* **Model（数据层）**：负责处理**数据逻辑**。
* **View（视图层）**：负责处理**视图显示**，在**Android**中使用**xml**或者**Java/Kotlin**代码去实现视图，**Activity**和**Fragment**承担了此层的责任。
* **ViewModel**：负责连接**Model**层和**View**层，是这两层的中间纽带，负责处理**业务逻辑**，**View**层和**ViewModel**层是**双向绑定**的，**View**层的变动会**自动**反映在**ViewModel**层，**ViewModel**层的变动也会**自动**反映在**View**层。

使用**MVVM**后，每一层的职责也更加清晰了，也方便做**单元测试**，同时因为**View**层和**ViewModel**层是**双向绑定**，开发者不需要再去主动处理部分逻辑了，减少了不少**胶水代码**，如果使用了一些**数据绑定**的库，例如在**Android**中的**DataBinding**，可以减少更加多的**胶水代码**。

# 实践

我使用**GitHub**的**API**开发了一个简单的客户端，用**MVVM**来搭建，使用**Kotlin**编写，界面如下图所示：

**登录**：

![LoginPage.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/LoginPage.png)

**首页**：

![MainPage.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/MainPage.png)

**个人中心**：

![PersonalCenterPage.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/PersonalCenterPage.png)

## 架构设计

整体分为**六部分**，每一部分都按**业务逻辑**区分：

### data

**data**存放**数据**相关的代码，如图所示：

![data.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/data.png)

* **local**：**本地数据**，存放**本地存储逻辑（MMKV相关的逻辑）**，例如：**UserLocalDataSource（用户本地数据源）**。
* **model**：**数据类**，存放**请求数据类（request）**和**响应数据类（response）**，例如：**LoginRequestData（登录请求数据类）**、**UserAccessTokenData（用户访问Token数据类）**、**UserInfoData（用户信息数据类）**、**ListData（基础的列表数据类）**和**Repository（GitHub仓库请求和响应数据类）**。
* **remote**：**远程数据**，存放**网络请求逻辑（OkHttp3和Retrofit2相关的逻辑）**，例如：**UserRemoteDataSource（用户远程数据源）**和**RepositoryRemoteDataSource（GitHub仓库远程数据源）**。
* **repository**：**仓库**，例如：**UserInfoRepository（用户信息仓库）**和**GitHubRepository（GitHub仓库）**。

**Repository**持有**LocalDataSource（本地数据源）**和**RemoteDataSource（远程数据源）**的**引用**，暴露相关的数据出去，外界不必关心**repository**内部是如何处理数据的。

### di

**di**存放**依赖注入**相关的代码。

**Dagger2**版本，如图所示：

![di.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/di.png)

* **ApplicationComponent**：**Application组件**，将**AndroidSupportInjectionModule**、**ApplicationModule**、**NetworkModule**、**RepositoryModule**、**MainModule**、**UserModule**和**GitHubRepositoryModule**注入到**Application**。
* **ApplicationModule**：提供**跟随Application生命周期**的业务**模块**，例如：**LocalDataSource（本地数据源）**和**RemoteDataSource（远程数据源）**。
* **GitHubRepositoryModule**：**业务模块**，提供**GitHub仓库业务**的**模块**。
* **MainModule**：**业务模块**，提供**main（启动页和主页）业务**的**模块**。
* **NetworkModule**：**网络模块**，例如：**OkHttp3**和**Retrofit2**。
* **RepositoryModule**：**仓库模块**，例如：**UserInfoRepository（用户信息仓库）**和**GitHubRepository（GitHub仓库）**。
* **UserModule**：**业务模块**，提供**用户业务**的**模块**。
* **ViewModelFactory**：**ViewModel工厂**，创建不同业务的**ViewModel**。

**Koin**版本：如图所示：

* **ApplicationModule**：存放**ApplicationModule**、**NetworkModule**、**RepositoryModule**、**MainModule**、**UserModule**和**GitHubRepositoryModule**，并且生成**ApplicationModules**的**List**提供**Koin**使用。

### ui

**ui**存放**UI**相关的代码，例如：**Activity**、**Fragment**、**ViewModel**和**自定义View**等等，如图所示：

![ui.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/ui.png)

* **main**：**main（启动页和主页）**相关的**Activity**和**ViewModel**代码。
* **recyclerview**：**RecyclerView**相关的代码，包括**BaseViewHolder**、**BaseViewType**、**NoDataViewType**、**BaseDataBindingAdapter**和**MultiViewTypeDataBindingAdapter**。
* **repository**：**GitHub仓库**相关的**Activity**、**Fragment**、**ViewModel**和**Adapter**代码。
* **user**：**用户**相关的**Activity**、**Fragment**和**ViewModel**代码。
* **BaseActivity**：**Acitivity**的**基类**。
* **BaseFragment**：**Fragment**的**基类**。
* **BaseViewModel**：**ViewModel**的**基类**。
* **NoViewModel**：一个**继承BaseViewModel**的类，如果该**Acitivity**或者**Fragment**不需要用到**ViewModel**的话可以使用这个类。

**ViewModel**持有**Repository**的**引用**，从**Repository**拿到想要的数据；**ViewModel**不会持有任何**View**层（例如：**Activity（包括xml）**、**Fragment（包括xml）**）的引用，通过**双向绑定框架（DataBinding）**获取**View**层反馈给**ViewModel**层的数据，并且对这些数据进行操作。

### utils

**utils**存放**工具文件**，如图所示：

![utils.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/utils.png)

* **ActivityExt**：存放**Activity**的**扩展函数**。
* **BindingAdapters**：存放使用**DataBinding**的**@BindingAdapters**注解的代码。
* **BooleanExt**：存放**Boolean**的扩展函数，如果想深入了解的话，可以看下我这篇文章：[Kotlin系列——泛型型变](https://juejin.im/post/5e3318e55188254bd37e3903)
* **DateUtils**：存放**日期**相关的代码。
* **FragmentExt**：存放**Fragment**的**扩展函数**。
* **GsonExt**：存放**Gson**相关的**扩展函数**。
* **Language**：存放**GitHub仓库**相关的**名字**和**图片**。
* **OnTabSelectedListenerBuilder**：存放**OnTabSelectedListener**相关的代码，用作使用**DSL**，如果想深入了解的话，可以看下我这篇文章：[Kotlin系列——DSL](https://juejin.im/post/5d949ce56fb9a04e031bebb6)
* **Preferences**：存放**MMKV**相关的代码，如果想深入了解的话，可以看下我这篇文章：[Kotlin系列——封装MMKV及其相关Kotlin特性](https://juejin.im/post/5e2c867ae51d451c7d2a688b)
* **SingleLiveEvent**：一个**生命周期感知**的**观察对象**，在**订阅**后只发送新的功能，可以用于**导航**和**SnackBar**消息等事件，它可以避免一个常见问题，就是如果**观察者**处于**活跃状态**，在**配置更改（例如：旋转）**的时候是可以**发射事件**，这个类可以解决这个问题，它只在你**显式**地调用**setValue()**方法或者**call()**方法，它才会调用**可观察对象**。
* **ToastExt**：存放**Toast**的**扩展函数**。

### 前缀AndroidGenericFramework的文件

如图所示：

![PrefixAndroidGenericFrameworkFile.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/PrefixAndroidGenericFrameworkFile.png)

* **AndroidGenericFrameworkAppGlideModule**：定义在**应用程序（Application）**内初始化**Glide**时要使用的一组**依赖项**和**选项**，要注意的是，在一个**应用程序（Application）**中**只能存在一个AppGlideModule**，如果是**库（Libraries）**就**必须**使用**LibraryGlideModule**。
* **AndroidGenericFrameworkApplication**：本框架的**Application**。
* **AndroidGenericFrameworkConfiguration**：存放本框架的**配置信息**。
* **AndroidGenericFrameworkExtra**：存放**Activity**和**Fragment**的**附加数据的名称**。
* **AndroidGenericFrameworkFragmentTag**：存放**Fragment**的**标记名**，这个**标记名**是为了以后使用**FragmentManager**的**findFragmentByTag(String)**方法的时候**检索Fragment**。

### 单元测试

如图所示：

![test.png](https://github.com/TanJiaJunBeyond/AndroidGenericFramework/raw/master/screenshot/test.png)

* **data**：**FakeDataSource**用来创建**假的数据源**，**UserRemoteDataSourceTest（用户远程数据源测试类）**和**RepositoryRemoteDataSourceTest（GitHub仓库远程数据源测试类）**都是**模拟API调用**。
* **utils**：存放**工具文件**的**测试类**。
* **viewmodel**：存放**ViewModel**的**测试类**。

下面我来介绍下使用到的**Android架构组件**和**库**。

## OkHttp3和Retrofit2

**网络请求库**使用了基于**OkHttp3**封装的**Retrofit2**，框架部分代码如下：

```kotlin
// NetworkModule.kt
/**
 * Created by TanJiaJun on 2020/4/4.
 */
@Suppress("unused")
@Module
open class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(localDataSource: UserLocalDataSource): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(BasicAuthInterceptor(localDataSource))
                    .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(String.format("%1\$s://%2\$s/", "https", AndroidGenericFrameworkConfiguration.HOST))
                    .build()

}
```

**Retrofit2.6**以后支持**Kotlin**的**协程**，和旧版本有如下区别：

1. 可以直接作用于**挂起函数（suspend fun）**。
2. 可以直接返回我们想要的**数据对象**，而不再返回**Deferred<T>**对象。
3. 不再需要调用**协程**中**await**函数，因为**Retrofit**已经帮我们调用了。

框架部分代码如下：

```kotlin
// RepositoryRemoteDataSource.kt
interface Service {

    @GET("search/repositories")
    suspend fun fetchRepositories(@Query("q") query: String,
                                  @Query("sort") sort: String = "stars"): ListData<RepositoryResponseData>

}
```

## Glide v4

**图片加载库**使用了**Glide v4**，我这里用到**DataBinding**组件中的**@BindingAdapter**注解，框架部分代码如下：

```kotlin
// BindingAdapters.kt
@BindingAdapter(value = ["url", "placeholder", "error"], requireAll = false)
fun ImageView.loadImage(url: String?, placeholder: Drawable?, error: Drawable?) =
        Glide
                .with(context)
                .load(url)
                .placeholder(placeholder ?: context.getDrawable(R.mipmap.ic_launcher))
                .error(error ?: context.getDrawable(R.mipmap.ic_launcher))
                .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
                .into(this)
```

## Android Jetpack

**Android Jetpack**是一套**库**、**工具**和**指南**，可以帮助开发者**更轻松**地编写**优质应用**，这些**组件**可以帮助开发者遵循**最佳做法**，让开发者**摆脱编写样板代码的工作**，并且**简化复杂任务**，以便开发者将精力集中放在所需的代码上。我使用了**DataBinding**、**Lifecycle**、**LiveData**、**ViewModel**，下面我大概地介绍下。

### DataBinding

**DataBinding**是实现**MVVM**的**核心架构组件**，它有如下**优点**：

1. 可以降低布局和逻辑的耦合度，使代码逻辑更加清晰。
2. 可以省去**findViewById**这样的代码，大量减少**View**层的代码。
3. 数据能**单向**和**双向**绑定到**layout**文件。
4. 能够自动进行**空判断**，可以避免**空指针异常**。

框架部分代码如下：

```xml
<!-- activity_personal_center.xml -->
<ImageView
    android:id="@+id/iv_head_portrait"
    error="@{@drawable/ic_default_avatar}"
    placeholder="@{@drawable/ic_default_avatar}"
    url="@{viewModel.avatarUrl}"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:layout_marginTop="16dp"
    android:contentDescription="@string/head_portrait"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/divider_line"
    tools:background="@drawable/ic_default_avatar" />
```

### Lifecycle

**Lifecycle**组件可以执行操作来响应**Activity**和**Fragment**的**生命周期**状态的变化。

**LiveData**和**ViewModel**都使用到**Lifecycle**组件，框架部分代码如下：

```kotlin
// LoginFragment.kt
override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        with(binding) {
            lifecycleOwner = this@LoginFragment
            viewModel = this@LoginFragment.viewModel
            handlers = this@LoginFragment
        }.also {
            registerLoadingProgressBarEvent()
            registerSnackbarEvent()
            observe()
        }
```

我们看下**ViewDataBinding**的**setLifecycleOwner**方法，代码如下：

```java
// ViewDataBinding.java
@MainThread
public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
    if (mLifecycleOwner == lifecycleOwner) {
        return;
    }
    if (mLifecycleOwner != null) {
        mLifecycleOwner.getLifecycle().removeObserver(mOnStartListener);
    }
    mLifecycleOwner = lifecycleOwner;
    if (lifecycleOwner != null) {
        if (mOnStartListener == null) {
            mOnStartListener = new OnStartListener(this);
        }
        lifecycleOwner.getLifecycle().addObserver(mOnStartListener);
    }
    for (WeakListener<?> weakListener : mLocalFieldObservers) {
        if (weakListener != null) {
            weakListener.setLifecycleOwner(lifecycleOwner);
        }
    }
}
```

这里的**LifecyclerOwner**是一个具有**Android生命周期**的类，**自定义组件**可以使用它的**事件**来处理**生命周期**更改，而无需在**Activity**或者**Fragment**实现任何代码。

### LiveData

**LiveData**是一种**可观察**的**数据存储器类**，它具有**生命周期感知能力**，遵循**应用组件**（例如：**Activity**、**Fragment**、**Service**（可以使用**LifecycleService**，它是实现了**LifecycleOwner**接口的**Service**））的生命周期，这种感知能力确保**LiveData**仅更新处于**活跃生命周期状态**的**应用组件观察者**。

我之前写过一篇关于**LiveData**的文章，大家可以阅读一下：

[Android Jetpack系列——LiveData源码分析](https://juejin.im/post/5e1228cd5188253a7a1e05cd)

框架部分代码如下：

```kotlin
// LoginViewModel.kt
val username = MutableLiveData<String>()
val password = MutableLiveData<String>()

private val _isLoginEnable = MutableLiveData<Boolean>()
val isLoginEnable: LiveData<Boolean> = _isLoginEnable

val isLoginSuccess = MutableLiveData<Boolean>()

fun checkLoginEnable() {
    _isLoginEnable.value = !username.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
}
```

### ViewModel

**ViewModel**是一个负责准备和管理**Activity**或者**Fragment**的类，它还可以处理**Activity**和**Fragment**与应用程序其余部分的通信（例如：调用业务逻辑类）。

**ViewModel**总是在一个**Activity**或者一个**Fragment**创建的，并且只要对应的**Activity**或者**Fragment**处于**活动状态**的话，它就会被保留（例如：如果它是个**Activity**，就会直到它**finished**）。

换句话说，这意味着一个**ViewModel**不会因为**配置的更改**（例如：**旋转**）而被销毁，所有的新实例将被重新连接到现有的**ViewModel**。

**ViewModel**的目的是**获取**和**保存Activity**或者**Fragment**所需的信息，**Activity**或者**Fragment**应该能够观察到**ViewModel**中的变化，通常通过**LiveData**或者**Android Data Binding**公开这些信息。

我之前写过一篇关于**ViewModel**的文章，大家可以阅读一下：

[Android Jetpack系列——ViewModel源码分析](https://juejin.im/post/5df67a5651882512533a8706)

框架部分代码如下：

```kotlin
// RepositoryViewModel.kt
/**
 * Created by TanJiaJun on 2020-02-07.
 */
class RepositoryViewModel @Inject constructor(
        private val repository: GitHubRepository
) : BaseViewModel() {

    private val _isShowRepositoryView = MutableLiveData<Boolean>()
    val isShowRepositoryView: LiveData<Boolean> = _isShowRepositoryView

    private val _repositories = MutableLiveData<List<RepositoryData>>()
    val repositories: LiveData<List<RepositoryData>> = _repositories

    fun getRepositories(languageName: String) =
            launch(
                    uiState = UIState(isShowLoadingView = true, isShowErrorView = true),
                    block = { repository.getRepositories(languageName) },
                    success = {
                        if (it.isNotEmpty()) {
                            _repositories.value = it
                            _isShowRepositoryView.value = true
                        }
                    }
            )

}
```

### 协程

**协程**源自**Simula**和**Modula-2**语言，它是一种**编程思想**，并不局限于特定的语言，在**1958年**的时候，**Melvin Edward Conway**提出这个术语并用于构建**汇编程序**。在**Android**中使用它可以简化**异步执行**的代码，它是在版本**1.3**中添加到**Kotlin**。

在**Android**平台上，**协程**有助于解决**两个**主要问题：

* 管理长时间运行的任务，如果管理不当，这些任务可能会阻塞**主线程**并导致你的应用**界面冻结**。
* 提供**主线程**安全性，或者从**主线程**安全地调用网络或者磁盘操作。

#### 管理长时间运行的任务

在**Android**平台上，每个应用都有一个用于处理界面并且管理用户交互的主线程。如果你的应用为**主线程**分配的工作太多，会导致**界面呈现速度缓慢**或者**界面冻结**，**对触摸事件的响应速度很慢**，例如：**网络请求**、**JSON解析**、**读取或者写入数据库**、**遍历大型列表**，这些都应该在**工作线程**完成。

**协程**在常规函数的基础上添加了**两项**操作，用于处理长时间运行的任务。在**invoke**或者**call**和**return**之外，**协程**添加了**suspend**和**resume**：

* **suspend**用于**暂停**执行当前**协程**，并保存**所有的局部变量**。
* **resume**用于让**已暂停**的**协程**从其**暂停处**继续执行。

要调用**suspend函数**，只能从其他**suspend函数**进行调用，或者通过使用**协程构建器**（例如：**launch**）来启动新的**协程**。

**Kotin**使用**堆栈帧**来管理**要运行哪个函数**以及**所有的局部变量**。**暂停协程**时会**复制并保存**当前的**堆栈帧**以供稍后使用；**恢复协程**时会将**堆栈帧**从其保存位置复制回来，然后函数**再次开始运行**。

#### 使用协程确保主线程安全

**Kotlin协程**使用**调度程序**来确定哪些**线程**用于执行**协程**，所有**协程**都必须在**调度程序**中运行，**协程**可以**自行暂停**，而**调度程序**负责将其恢复。

**Kotlin**提供了**三个调度程序**，可以使用它们来指定应在何处运行**协程**：

* **Dispatchers.Main**：使用此**调度程序**可在**Android主线程**上运行**协程**，只能用于**界面交互**和**执行快速工作**，例如：**调用suspend函数**、**运行Android界面框架操作**和**更新LiveData对象**。
* **Dispatchers.IO**：此**调度程序**适合在**主线程之外**执行**磁盘或者网络I/O**，例如：**操作数据库（使用Room）**、**从文件中读取数据或者向文件中写入数据**和**运行任何网络操作**。
* **Dispatcher.Default**：此**调度程序**适合在**主线程之外**执行**占用大量CPU资源的工作**，例如：**对列表排序**和**解析JSON**。

#### 指定CoroutineScope

在定义**协程**时，必须指定其**CoroutineScope**，**CoroutineScope**可以管理**一个**或者**多个**相关的**协程**，可以使用它在该范围内启动**新协程**。

**与调度程序不同，CoroutineScope不运行协程。**

**CoroutineScope**的**一项重要功能**就是在**用户离开应用中内容区域时停止执行协程**，**可以确保所有正在运行的操作都能正确停止**。

在**Android**平台上，可以将**CoroutineScope**实现与组件的**生命周期**相关联，例如：**Lifecycle**和**ViewModel**，这样可以避免**内存泄漏**和不再对与用户相关的**Activity**或者**Fragment**执行额外的工作。

#### 启动协程

可以通过以下**两种**方式来启动**协程**：

* **launch**：**可以启动新协程，但是不将结果返回给调用方。**
* **async**：**可以启动新协程，并且允许使用await暂停函数返回结果。**

同时我还使用了**Kotlin**的**流（Flow）**，它的设计灵感来源于**响应式流（Reactive Streams）**，所以如果开发者熟悉**RxJava**的话，也应该很快就能熟悉它。

我之前写过几篇关于**RxJava**的文章，大家可以阅读一下：

[RxJava2源码分析——订阅](https://juejin.im/post/5daaf8e4e51d4524ad10d58c)

[RxJava2源码分析——线程切换](https://juejin.im/post/5db5e0f86fb9a0205f0ed94f)

[RxJava2源码分析——Map操作符](https://juejin.im/post/5db9d27ae51d4529df5f6f93)

[RxJava2源码分析——FlatMap和ConcatMap及其相关并发编程分析](https://juejin.im/post/5dcda3e65188254edb372056)

框架部分代码如下：

```kotlin
// LoginViewModel.kt
@ExperimentalCoroutinesApi
@FlowPreview
fun login() =
        launchUI {
            launchFlow {
                repository.run {
                    cacheUsername(username.value ?: "")
                    cachePassword(password.value ?: "")
                    authorizations()
                }
            }
                    .flatMapMerge {
                        launchFlow { repository.getUserInfo() }
                    }
                    .flowOn(Dispatchers.IO)
                    .onStart { uiLiveEvent.showLoadingProgressBarEvent.call() }
                    .catch {
                        val responseThrowable = ExceptionHandler.handleException(it)
                        uiLiveEvent.showSnackbarEvent.value = "${responseThrowable.errorCode}:${responseThrowable.errorMessage}"
                    }
                    .onCompletion { uiLiveEvent.dismissLoadingProgressBarEvent.call() }
                    .collect {
                        repository.run {
                            cacheUserId(it.id)
                            cacheName(it.login)
                            cacheAvatarUrl(it.avatarUrl)
                        }
                        isLoginSuccess.value = true
                    }
        }
```

### Dagger2

**Dagger2**是针对**Java**和**Android**的**全静态**、**编译阶段完成依赖注入**的**框架**。

**Dagger**这个库的取名不仅仅是来自它的本意——**匕首**，**Jake Wharton**在介绍**Dagger**的时候指出，**Dagger**的意思是**DAG-er**，**DAG**的意思**有向无环图（Directed Acyclic Graph）**，也就是说**Dagger**是一个**基于有向无环图结构的依赖注入库**，因此**Dagger**在使用过程中不能出现**循环依赖**。

**Square**公司受到**Guice**的启发开发了**Dagger**，它是一种**半静态**、**半运行时**的**依赖注入框架**，虽然说**依赖注入**是**完全静态**的，但是生成**有向无环图**还是基于**反射**来实现，这无论在**大型服务端应用**或者**Android应用**上都**不是最优方案**，然后**Google**的工程师**fork**了这个项目后，受到**AutoValue**项目的启发，对其进行改造，就有了现在这个**Dagger2**，**Dagger2**和**Dagger**比较的话，有如下区别：

* **更好的性能**：**Google**声称**提高**了**13%**的**处理性能**，没有使用**反射**生成**有向无环图**，而是在**编译阶段**生成。
* **更高效和优雅，而且更容易调试**：作为**升级版的Dagger**，从**半静态**变成**完全静态**，从**Map式API**变成**申明式API（例如：@Module）**，生成的代码更加**高效**和**优雅**，一旦出错在**编译阶段**就能发现。

因为**Dagger2**没使用**反射**，**缺乏动态机制**，所以丧失一定的**灵活性**，但是总体来说是**利远远大于弊**的。

我在**主分支（master）**使用的是**Dagger2**和相关的**Dagger-Android**，框架部分代码如下：

```kotlin
// ApplicationComponent.kt
/**
 * Created by TanJiaJun on 2020/3/4.
 */
@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            ApplicationModule::class,
            NetworkModule::class,
            RepositoryModule::class,
            MainModule::class,
            UserModule::class,
            GitHubRepositoryModule::class
        ]
)
interface ApplicationComponent : AndroidInjector<AndroidGenericFrameworkApplication> {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance applicationContext: Context): ApplicationComponent

    }

}
```

### Koin

**Koin**是一个面向**Kotlin**开发人员实用的**轻量级依赖注入框架**。

官方声称是用**纯Kotlin编写**，只使用**函数解析**，**没有代理**、**没有代码生成**、**没有反射**。

我在**分支mvvm-koin**使用的是**Koin**，框架部分代码如下：

```kotlin
// ApplicationModule.kt
/**
 * Created by TanJiaJun on 2020/5/5.
 */
val applicationModule = module {
    single {
        UserLocalDataSource(MMKV.mmkvWithID(
                AndroidGenericFrameworkConfiguration.MMKV_ID,
                MMKV.SINGLE_PROCESS_MODE,
                AndroidGenericFrameworkConfiguration.MMKV_CRYPT_KEY
        ))
    }

    single { UserRemoteDataSource(get()) }

    single { RepositoryRemoteDataSource(get()) }
}

val networkModule = module {
    single<OkHttpClient> {
        OkHttpClient.Builder()
                .connectTimeout(AndroidGenericFrameworkConfiguration.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(AndroidGenericFrameworkConfiguration.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(BasicAuthInterceptor(get()))
                .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
                .client(get())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(String.format("%1\$s://%2\$s/", SCHEMA_HTTPS, AndroidGenericFrameworkConfiguration.HOST))
                .build()
    }
}

val repositoryModule = module {
    single { UserInfoRepository(get(), get()) }

    single { GitHubRepository(get()) }
}

val mainModule = module {
    scope<SplashActivity> {
        viewModel { SplashViewModel(get()) }
    }

    scope<MainActivity> {
        viewModel { MainViewModel(get()) }
    }
}

val userModule = module {
    scope<LoginFragment> {
        viewModel { LoginViewModel(get()) }
    }

    scope<PersonalCenterActivity> {
        viewModel { PersonalCenterViewModel(get()) }
    }
}

val githubRepositoryModule = module {
    scope<RepositoryFragment> {
        viewModel { RepositoryViewModel(get()) }
    }
}

val applicationModules = listOf(
        applicationModule,
        networkModule,
        repositoryModule,
        mainModule,
        userModule,
        githubRepositoryModule
)

private const val SCHEMA_HTTPS = "https"
```

### MMKV

**MMKV**是基于**mmap内存映射**的**key-value**组件，底层**序列化/反序列化**使用**protobuf**实现，**性能高**，**稳定性强**，而且**Android**这边还支持**多进程**。

我之前写过一篇关于**MMKV**的文章，大家可以阅读一下：

[Kotlin系列——封装MMKV及其相关Kotlin特性](https://juejin.im/post/5e2c867ae51d451c7d2a688b)

我使用**MMKV**代替**Android**组件中的**SharedPreferences**，作为**本地存储数据组件**，框架部分代码如下：

```kotlin
// Preferences.kt
/**
 * Created by TanJiaJun on 2020-01-11.
 */
private inline fun <T> MMKV.delegate(
        key: String? = null,
        defaultValue: T,
        crossinline getter: MMKV.(String, T) -> T,
        crossinline setter: MMKV.(String, T) -> Boolean
): ReadWriteProperty<Any, T> =
        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T =
                    getter(key ?: property.name, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                setter(key ?: property.name, value)
            }
        }

fun MMKV.boolean(
        key: String? = null,
        defaultValue: Boolean = false
): ReadWriteProperty<Any, Boolean> =
        delegate(key, defaultValue, MMKV::decodeBool, MMKV::encode)

fun MMKV.int(key: String? = null, defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
        delegate(key, defaultValue, MMKV::decodeInt, MMKV::encode)

fun MMKV.long(key: String? = null, defaultValue: Long = 0L): ReadWriteProperty<Any, Long> =
        delegate(key, defaultValue, MMKV::decodeLong, MMKV::encode)

fun MMKV.float(key: String? = null, defaultValue: Float = 0.0F): ReadWriteProperty<Any, Float> =
        delegate(key, defaultValue, MMKV::decodeFloat, MMKV::encode)

fun MMKV.double(key: String? = null, defaultValue: Double = 0.0): ReadWriteProperty<Any, Double> =
        delegate(key, defaultValue, MMKV::decodeDouble, MMKV::encode)

private inline fun <T> MMKV.nullableDefaultValueDelegate(
        key: String? = null,
        defaultValue: T?,
        crossinline getter: MMKV.(String, T?) -> T,
        crossinline setter: MMKV.(String, T) -> Boolean
): ReadWriteProperty<Any, T> =
        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T =
                    getter(key ?: property.name, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                setter(key ?: property.name, value)
            }
        }

fun MMKV.byteArray(
        key: String? = null,
        defaultValue: ByteArray? = null
): ReadWriteProperty<Any, ByteArray> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeBytes, MMKV::encode)

fun MMKV.string(key: String? = null, defaultValue: String? = null): ReadWriteProperty<Any, String> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeString, MMKV::encode)

fun MMKV.stringSet(
        key: String? = null,
        defaultValue: Set<String>? = null
): ReadWriteProperty<Any, Set<String>> =
        nullableDefaultValueDelegate(key, defaultValue, MMKV::decodeStringSet, MMKV::encode)

inline fun <reified T : Parcelable> MMKV.parcelable(
        key: String? = null,
        defaultValue: T? = null
): ReadWriteProperty<Any, T> =
        object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>): T =
                    decodeParcelable(key ?: property.name, T::class.java, defaultValue)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                encode(key ?: property.name, value)
            }
        }
```

可以这样使用，框架部分代码如下：

```kotlin
// UserLocalDataSource.kt
var accessToken by mmkv.string("user_access_token", "")
var userId by mmkv.int("user_id", -1)
var username by mmkv.string("username", "")
var password by mmkv.string("password", "")
var name by mmkv.string("name", "")
var avatarUrl by mmkv.string("avatar_url", "")
```

### ViewPager2

框架中在展示**GitHub**的**仓库**的时候用到了**ViewPager2**，比起**ViewPager**，有以下几个**好处**：

* **支持垂直方向分页**：**ViewPager2**除了支持**水平方向**分页，也支持**垂直方向**分页，可以通过**android:orientation**属性或者**setOrientation()**方法来启动**垂直分页**，代码如下：

  ```xml
  android:orientation="vertical"
  ```

* **支持从右到做（RTL）**：**ViewPager2**会根据**语言环境**自动启动**从右到做（RTL）**分页，可以通过设置**android:layoutDirection**属性或者**setLayoutDirection()**方法来启动**RTL**分页，代码如下：

  ```xml
  android:layoutDirection="rtl"
  ```

框架部分代码如下：

```xml
<!-- activity_main.xml -->
<androidx.viewpager2.widget.ViewPager2
    android:id="@+id/vp_repository"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/tl_repository" />
```

### MockK

**MockK**一个专门为**Kotlin**这门语言打造的**测试框架**。在**Java**中，我们常用的是**Mockito**，但是如果我们使用**Kotlin**的话，就会遇到一些问题，常见的问题如下：

* **不能测试静态方法**：可以使用**PowerMock**解决。

* **Mockito cannot mock/spy because:-final class**：这是因为在**Kotlin**中**任何类**预设都是**final**的，**Mockito**预设情况下不能**mock**一个**final**的类。
* **java.lang.illegalStateException:anyObjecet() must not be null**：如果我们使用**eq()**、**any()**、**capture()**和**argumentCaptor()**的话就会遇到这个问题了，因为这些方法返回的对象可能是**null**，如果作用在一个**非空**的参数的话，就会报这个异常了，解决办法是可以使用如下文件：
* **when**要加上**反引号**才能使用：因为**when**是**Kotlin**中的**关键字**。

**Kotlin**和**Mockito**同时使用会有如上说的种种不便，最后我决定使用**MockK**这个**库**，我使用的**测试**相关的**库**如下：

```groovy
// build.gradle(:app)
testImplementation "junit:junit:$junitVersion"
testImplementation "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
testImplementation "io.mockk:mockk:$mockkVersion"
testImplementation "com.google.truth:truth:$truthVersion"
testImplementation "org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinxCoroutinesVersion"
testImplementation "android.arch.core:core-testing:$coreTestingVersion"
```

* **com.squareup.okhttp3:mockwebserver**：用来**模拟Web服务器**的。

* **com.google.truth:truth**：可以使**测试断言**和**失败消息**更具有**可读性**，与**AssertJ**相似，它支持很多**JDK**和**Guava**类型，并且可以扩展到其他类型。

框架部分代码如下：

```kotlin
// LoginViewModelTest.kt
@ExperimentalCoroutinesApi
@FlowPreview
@Test
fun login_success() {
    runBlocking {
        viewModel.username.value = "1120571286@qq.com"
        viewModel.password.value = "password"
        coEvery { repository.authorizations() } returns userAccessTokenData
        coEvery { repository.getUserInfo() } returns userInfoData
        viewModel.login()
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isLoginSuccess.observeForever(observer)
        viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
        verify { observer.onChanged(match { it }) }
    }
}

@ExperimentalCoroutinesApi
@FlowPreview
@Test
fun login_failure() {
    runBlocking {
        viewModel.username.value = "1120571286@qq.com"
        viewModel.password.value = "password"
        coEvery { repository.authorizations() } returns userAccessTokenData
        coEvery { repository.getUserInfo() } throws Throwable("UnknownError")
        viewModel.login()
        val observer = mockk<Observer<String>>(relaxed = true)
        viewModel.uiLiveEvent.showSnackbarEvent.observeForever(observer)
        viewModel.viewModelScope.coroutineContext[Job]?.children?.forEach { it.join() }
        verify { observer.onChanged(match { it == "0:UnknownError" }) }
    }
}
```



**我的GitHub：**[TanJiaJunBeyond](https://github.com/TanJiaJunBeyond)

**Android通用框架：**[Android通用框架](https://github.com/TanJiaJunBeyond/AndroidGenericFramework)

**我的掘金：**[谭嘉俊](https://juejin.im/user/593f7b33fe88c2006a37eb9b)

**我的简书：**[谭嘉俊](https://www.jianshu.com/u/257511d0c878)

**我的CSDN：**[谭嘉俊](https://blog.csdn.net/qq_20417381)