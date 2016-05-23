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

    /**
     * Fetches all job info IDs from storage.
     * 
     * @return
     * @since 0.1.3.2
     */
    public String[] getAllJobInfoIds();

    /*----------------------------------------------------------------------*/

    /**
     * Fetches an existing job execution info from storage.
     * 
     * @param id
     * @return
     * @since 0.1.3
     */
    public JobExecInfoBo getJobExecInfo(String id);

    /**
     * Creates a new of updates an existing job execution info.
     * 
     * @param jobExecInfo
     * @return
     * @since 0.1.3
     */
    public boolean createOrUpdate(JobExecInfoBo jobExecInfo);

    /*----------------------------------------------------------------------*/
    /**
     * Persists a new job template to storage.
     * 
     * @param jobTemplate
     * @return
     * @since 0.1.3.3
     */
    public boolean create(JobTemplateBo jobTemplate);

    /**
     * Deletes an existing job template from storage.
     * 
     * @param jobTemplate
     * @return
     * @since 0.1.3.3
     */
    public boolean delete(JobTemplateBo jobTemplate);

    /**
     * Updates an exiting job template.
     * 
     * @param jobTemplate
     * @return
     * @since 0.1.3.3
     */
    public boolean update(JobTemplateBo jobTemplate);

    /**
     * Fetches an existing job template from storage.
     * 
     * @param id
     * @return
     */
    public JobTemplateBo getJobTemplate(String id);

    /**
     * Fetches all job template IDs from storage.
     * 
     * @return
     * @since 0.1.3.3
     */
    public String[] getAllJobTemplateIds();
}
