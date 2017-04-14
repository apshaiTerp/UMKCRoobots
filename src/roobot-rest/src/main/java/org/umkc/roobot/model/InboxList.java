package org.umkc.roobot.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author AC010168
 *
 */
public class InboxList {

  private List<InboxEmailHeader> inboxList;
  
  public InboxList() {
    inboxList = new LinkedList<InboxEmailHeader>();
  }
  
  public void addEmailHeader(InboxEmailHeader header) {
    inboxList.add(header);
  }

  /**
   * @return the inboxList
   */
  public List<InboxEmailHeader> getInboxList() {
    return inboxList;
  }

  /**
   * @param inboxList the inboxList to set
   */
  public void setInboxList(List<InboxEmailHeader> inboxList) {
    this.inboxList = inboxList;
  }
}
