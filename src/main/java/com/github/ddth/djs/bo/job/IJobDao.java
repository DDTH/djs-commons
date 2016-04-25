package com.github.ddth.djs.bo.job;

/**
 * DAO to interact with {@link JobInfoBo} storage.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.1.0
 */
public interface IJobDao {
	/**
	 * Persists a new job info to storage.
	 * 
	 * @param jobInfo
	 * @return
	 */
	public boolean create(JobInfoBo jobInfo);

	/**
	 * Deletes an existing job info from storage.
	 * 
	 * @param jobInfo
	 * @return
	 */
	public boolean delete(JobInfoBo jobInfo);

	/**
	 * Updates an exiting job info.
	 * 
	 * @param jobInfo
	 * @return
	 */
	public boolean update(JobInfoBo jobInfo);

	/**
	 * Fetches an existing job info from storage.
	 * 
	 * @param id
	 * @return
	 */
	public JobInfoBo getJobInfo(String id);
}