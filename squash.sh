#!/bin/sh
set -e

# Get the current branch name.
branch=`git rev-parse --abbrev-ref HEAD`
# Determine the commit at which the current branch diverged.
ancestor=`git merge-base develop HEAD`
# Stash any uncommited, changed files.
git stash --all
# Revert the branch back to the ancestor SHA.
git reset --hard $ancestor
# Squash all commits from ancestor to previous SHA.
git merge --squash HEAD@{1}
# Perform the commit, prompting for the message.
git commit
# Fetch the latest changes from the upstream branch.
git fetch origin develop
# Rebase the current single commit onto the latest.
git rebase develop
# Restore previous uncommited changes, if any.
git stash pop || true

printf "\nDone! Push with 'git push -f origin %s'\n" "$branch"

exit 0 # Done
