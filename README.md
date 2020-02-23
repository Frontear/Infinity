# Infinity ![Infinity](https://github.com/Frontear/Infinity/workflows/Infinity/badge.svg?branch=fabric-rewrite) [![CodeFactor](https://www.codefactor.io/repository/github/frontear/infinity/badge)](https://www.codefactor.io/repository/github/frontear/infinity)
Infinity is a simple client designed in [Forge](https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.8.9.html). It adds a variety of features into minecraft, and also comes with performance improvements and bug fixes. This is possible due to [Mixin](https://github.com/SpongePowered/Mixin).

## Getting Started
Before starting, please make sure you have a thorough understanding of mixins and java. Additionally, we recommend [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/) as your Java IDE alongside the [MinecraftDev](https://github.com/minecraft-dev/MinecraftDev) plugin.
Please also make sure that you install the [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok) plugin and the [Manifold](https://plugins.jetbrains.com/plugin/10057-manifold) plugin.

- Clone this repository via using `git clone https://github.com/Frontear/Infinity.git Infinity`
- Run `task setupDecompWorkspace` either using `./gradlew` or through IntelliJ IDEA
- Use `./gradlew runClient` to test the code as if it were in production, we don't support nor recommend using `./gradlew genIntelliJRuns`

## License
This project is licensed under the [MIT License](https://tldrlegal.com/license/mit-license) &#8212; you can do whatever you want as long as you include the original copyright and license notice in any copy of the software/source.

## Libraries
This project makes use of the following libraries, each of which can have their licenses viewed:
- [SpongePowered/Mixin](https://github.com/SpongePowered/Mixin), license [MIT](https://github.com/SpongePowered/Mixin/blob/master/LICENSE.txt)
- [rzwitserloot/lombok](https://github.com/rzwitserloot/lombok), license [MIT](https://github.com/rzwitserloot/lombok/blob/master/LICENSE)
- [manifold-system/manifold](https://github.com/manifold-systems/manifold), license [Apache 2.0](https://github.com/manifold-systems/manifold/blob/master/LICENSE)
