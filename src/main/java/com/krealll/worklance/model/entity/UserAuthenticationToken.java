package com.krealll.worklance.model.entity;

public class UserAuthenticationToken {

    private Long tokenId;
    private String selector;
    private String validator;
    private Long userId;

    public UserAuthenticationToken(Long id, String selector, String validator, Long userId) {
        this.tokenId = id;
        this.selector = selector;
        this.validator = validator;
        this.userId = userId;
    }

    public UserAuthenticationToken(String selector, String validator, Long userId) {
        this.selector = selector;
        this.validator = validator;
        this.userId = userId;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthenticationToken token = (UserAuthenticationToken) o;
        if(tokenId != null ? !tokenId.equals(token.tokenId) : token.tokenId != null ){
            return false;
        }
        if(selector != null ? !selector.equals(token.selector) : token.selector != null ){
            return false;
        }
        if(validator != null ? !validator.equals(token.validator) : token.validator != null ){
            return false;
        }
        if(userId != null ? !userId.equals(token.userId) : token.userId != null ){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = tokenId != null ? tokenId.hashCode() : 0;
        hashCode = 31 * hashCode + (selector != null ? selector.hashCode() : 0);
        hashCode = 31 * hashCode + (validator != null ? validator.hashCode() : 0);
        hashCode = 31 * hashCode + (userId != null ? userId.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("token_id=").append(tokenId);
        stringBuilder.append(", selector=").append(selector).append('\'');
        stringBuilder.append(", validator=").append(validator).append('\'');
        stringBuilder.append(", userId=").append(userId).append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
