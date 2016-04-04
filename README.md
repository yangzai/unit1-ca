# University Souvenir Store Application
CA assignment by SE24 2FT for SG4101 Basic Software Engineering Discipline, NUS ISS.

## Contributors

### Setting up
[Set your IDE's indentation to use 4 spaces instead of tab.](http://eclipsesource.com/blogs/2013/07/09/invisible-chaos-mastering-white-spaces-in-eclipse/)

Clone this repository.

```
git clone https://github.com/yangzai/unit1-ca.git
```

Replicate data-sample contents to data.

```
cd unit1-ca
cp -r data-sample/ data
```

### Syncing feature branch with dev branch
When the dev branch is updated, you may want to merge them into the branch you are working on.

```
git checkout dev
git pull
git checkout your-feature
git status #chk if wrking dir is clean
```

If and only if your working directory is not clean,

```
git stash
git merge dev
git stash apply #fix conflicts if necessary
git stash drop
```

Otherwise,

```
git merge dev
```

### Extras
- [More on ```git stash```](https://youtu.be/KLEDKgMmbBI)

- [You should also consider ```git rebase``` over ```git merge```]
(https://www.atlassian.com/git/tutorials/merging-vs-rebasing)
