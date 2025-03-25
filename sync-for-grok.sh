#!/bin/bash

# This script provides commit summaries of local suitable to feed into Grok
# when describing a GitHub repo to it, and keeping those in sync for your
# conversation with Grok.

# Configurable variables
REPO_URL="https://github.com/binkley/layers-kt-2"
COMMIT_COUNT=5

echo "Recent commits for $REPO_URL:"
echo "--------------------------------"

# Get last N commits with hash, full message (subject + body), and changed files
git log -n "$COMMIT_COUNT" --pretty=format:"%H%n%B" --name-only | while IFS= read -r line; do
    # Check if line is a commit hash (starts with 40-char hex)
    if [[ "$line" =~ ^[0-9a-f]{40}$ ]]; then
        # Print separator before next commit (except first)
        if [[ -n "$commit_hash" ]]; then
            echo "--------------------------------"
        fi
        commit_hash="$line"
        echo "Commit: $REPO_URL/commit/$commit_hash"
        summary_next="true"
    # First non-blank line after hash is summary
    elif [[ "$summary_next" == "true" && -n "$line" && ! "$line" =~ ^\s*$ ]]; then
        echo "Summary: $line"
        echo "Full Message:"
        echo "  $line" # Repeat summary in full message
        summary_next="false"
    # Lines after summary until blank or next hash are message body
    elif [[ -n "$line" && ! "$line" =~ ^[0-9a-f]{40}$ && "$commit_hash" && "$summary_next" == "false" ]]; then
        if [[ ! "$line" =~ ^\s*$ ]]; then
            echo "  $line" # Preserve Markdown formatting
        elif [[ "$line" =~ ^\s*$ && "$files_started" != "true" ]]; then
            echo "Changed Files:"
            files_started="true"
        fi
    # Blank line after message starts files; reset on next hash
    elif [[ "$line" =~ ^\s*$ && "$files_started" == "true" ]]; then
        files_started="false"
    # File lines after blank
    elif [[ "$files_started" == "true" && -n "$line" ]]; then
        echo "  - $line"
    fi
done

# Add final separator if any commits were processed
if [[ -n "$commit_hash" ]]; then
    echo "--------------------------------"
fi
