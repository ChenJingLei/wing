package com.chinatel.caur2cdoauth2.service;

import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;

import java.util.Collection;

public class JPAApprovalStore implements ApprovalStore {
    @Override
    public boolean addApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> collection) {
        return false;
    }

    @Override
    public Collection<Approval> getApprovals(String s, String s1) {
        return null;
    }
}
