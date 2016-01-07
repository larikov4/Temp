package com.epam.hw1.repository.event;

/**
 * Event on friend addition.
 *
 * @author Yevhn on 07.01.2016.
 */
public class AddFriendEvent implements Event {
    private long versionId;
    private String friendUsername;

    public AddFriendEvent(long versionId, String friendUsername) {
        this.versionId = versionId;
        this.friendUsername = friendUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    @Override
    public long getVersionId() {
        return versionId;
    }
}