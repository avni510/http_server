module TimeLogger
  class LogTimeEntry
    attr_reader :id
    attr_reader :employee_id
    attr_reader :date
    attr_reader :hours_worked
    attr_reader :timecode
    attr_reader :client

    def initialize(params)
      @id = params[:id]
      @employee_id = params[:employee_id]
      @date = params[:date]
      @hours_worked = params[:hours_worked]
      @timecode = params[:timecode]
      @client = params[:client]
    end
  end
end
