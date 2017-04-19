module TimeLogger
  class Result 
    attr_reader :error_message
    attr_reader :code
    def initialize(error_message=nil, code = nil)
      @error_message = error_message
      @code = code
    end
    
    def valid?
      !@error_message
    end
  end
end
