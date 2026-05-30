package controller;

import dao.AuditDAO;
import dao.CandidateDAO;
import dao.ElectionDAO;
import model.Candidate;
import model.Election;
import utils.SessionManager;

import java.util.List;

/**
 * Controller for election and candidate management operations.
 */
public class ElectionController {

    private final ElectionDAO electionDAO;
    private final CandidateDAO candidateDAO;
    private final AuditDAO auditDAO;

    /** Constructs an ElectionController with default DAOs. */
    public ElectionController() {
        this.electionDAO = new ElectionDAO();
        this.candidateDAO = new CandidateDAO();
        this.auditDAO = new AuditDAO();
    }

    // ─── ELECTION OPERATIONS ────────────────────────

    /**
     * Creates a new election.
     *
     * @param election the {@link Election} to create
     * @return {@code true} if creation succeeded
     */
    public boolean createElection(Election election) {
        election.setCreatedBy(SessionManager.getCurrentUser().getUserId());
        boolean result = electionDAO.insertElection(election);
        if (result) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "ELECTION_CREATED", "Created election: " + election.getTitle());
        }
        return result;
    }

    /**
     * Updates an existing election.
     *
     * @param election the {@link Election} with updated data
     * @return {@code true} if update succeeded
     */
    public boolean updateElection(Election election) {
        boolean result = electionDAO.updateElection(election);
        if (result) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "ELECTION_UPDATED", "Updated election: " + election.getTitle());
        }
        return result;
    }

    /**
     * Deletes an election by ID.
     *
     * @param electionId the election ID
     * @return {@code true} if deletion succeeded
     */
    public boolean deleteElection(int electionId) {
        Election election = electionDAO.findById(electionId);
        boolean result = electionDAO.deleteElection(electionId);
        if (result && election != null) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "ELECTION_DELETED", "Deleted election: " + election.getTitle());
        }
        return result;
    }

    /**
     * Changes the status of an election.
     *
     * @param electionId the election ID
     * @param status     the new status
     * @return {@code true} if the status change succeeded
     */
    public boolean changeElectionStatus(int electionId, String status) {
        boolean result = electionDAO.updateStatus(electionId, status);
        if (result) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "ELECTION_STATUS_CHANGED",
                    "Election " + electionId + " status changed to: " + status);
        }
        return result;
    }

    /**
     * Retrieves all elections.
     *
     * @return the list of all elections
     */
    public List<Election> getAllElections() {
        return electionDAO.getAllElections();
    }

    /**
     * Retrieves elections by status.
     *
     * @param status the status to filter by
     * @return the list of matching elections
     */
    public List<Election> getElectionsByStatus(String status) {
        return electionDAO.getElectionsByStatus(status);
    }

    /**
     * Finds an election by ID.
     *
     * @param electionId the election ID
     * @return the {@link Election} if found
     */
    public Election getElection(int electionId) {
        return electionDAO.findById(electionId);
    }

    /**
     * Counts total elections.
     *
     * @return the election count
     */
    public int countElections() {
        return electionDAO.countElections();
    }

    /**
     * Counts elections by status.
     *
     * @param status the status to count
     * @return the count
     */
    public int countByStatus(String status) {
        return electionDAO.countByStatus(status);
    }

    // ─── CANDIDATE OPERATIONS ───────────────────────

    /**
     * Adds a candidate to an election.
     *
     * @param candidate the {@link Candidate} to add
     * @return {@code true} if addition succeeded
     */
    public boolean addCandidate(Candidate candidate) {
        boolean result = candidateDAO.insertCandidate(candidate);
        if (result) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "CANDIDATE_ADDED",
                    "Added candidate: " + candidate.getFullName() + " to election " + candidate.getElectionId());
        }
        return result;
    }

    /**
     * Updates a candidate.
     *
     * @param candidate the {@link Candidate} with updated data
     * @return {@code true} if update succeeded
     */
    public boolean updateCandidate(Candidate candidate) {
        boolean result = candidateDAO.updateCandidate(candidate);
        if (result) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "CANDIDATE_UPDATED",
                    "Updated candidate: " + candidate.getFullName());
        }
        return result;
    }

    /**
     * Deletes a candidate by ID.
     *
     * @param candidateId the candidate ID
     * @return {@code true} if deletion succeeded
     */
    public boolean deleteCandidate(int candidateId) {
        Candidate candidate = candidateDAO.findById(candidateId);
        boolean result = candidateDAO.deleteCandidate(candidateId);
        if (result && candidate != null) {
            auditDAO.log(SessionManager.getCurrentUser().getUserId(),
                    "CANDIDATE_DELETED",
                    "Deleted candidate: " + candidate.getFullName());
        }
        return result;
    }

    /**
     * Retrieves all candidates for an election.
     *
     * @param electionId the election ID
     * @return the list of candidates
     */
    public List<Candidate> getCandidates(int electionId) {
        return candidateDAO.getCandidatesByElection(electionId);
    }

    /**
     * Counts candidates in an election.
     *
     * @param electionId the election ID
     * @return the candidate count
     */
    public int countCandidates(int electionId) {
        return candidateDAO.countByElection(electionId);
    }
}
